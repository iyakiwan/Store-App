package com.mufti.test.storeapps.ui.screen.auth.login

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.mufti.test.storeapps.utils.constant.HttpCodeConstant
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.ActivityLoginBinding
import com.mufti.test.storeapps.data.Result
import com.mufti.test.storeapps.utils.ViewModelFactory
import com.mufti.test.storeapps.utils.extension.ActivityExtension.okAlertDialog
import com.mufti.test.storeapps.utils.extension.ActivityExtension.showToast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindow()

        setupViewModel()
        setupView()
        observeValidInput()
        observeLogin()

        viewModel.getTokenUser().observe(this) { token ->
            Log.d("Token User", token)
        }
    }

    private fun setupWindow() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)

        viewModel = ViewModelProvider(
            this, factory
        )[LoginViewModel::class.java]
    }

    private fun setupView() {
        binding.btnLogin.setOnClickListener {
            validateInput()
        }
    }

    private fun validateInput() {
        val username = binding.edLoginUsername.text.toString()
        val password = binding.edLoginPassword.text.toString()

        viewModel.validationInput(username, password)
    }

    private fun submitLogin() {
        val username = binding.edLoginUsername.text.toString()
        val password = binding.edLoginPassword.text.toString()

        viewModel.login(username, password)
    }

    private fun observeValidInput() {
        viewModel.validationInput.observe(this) {
            when (it) {
                LoginViewModel.LoginInputState.EmptyUsername -> {
                    binding.edLoginUsername.error = getString(R.string.alert_empty_username)
                }

                LoginViewModel.LoginInputState.EmptyPassword -> {
                    binding.edLoginPassword.error = getString(R.string.alert_empty_password)
                }

                LoginViewModel.LoginInputState.ValidInput -> {
                    submitLogin()
                }
            }
        }
    }

    private fun observeLogin() {
        viewModel.login.observe(this) {
            when (it) {
                is Result.Loading -> {
                    binding.pgLogin.isVisible = true
                }

                is Result.Success -> {
                    binding.pgLogin.isVisible = false
                    showToast(getString(R.string.message_complete_login))
//                    launchHomeActivity()
                }

                is Result.Error -> {
                    binding.pgLogin.isVisible = false
                    when (it.code) {
                        HttpCodeConstant.UNAUTHORIZED -> {
                            okAlertDialog(
                                title = getString(R.string.error),
                                message = getString(R.string.alert_invalid_login),
                                positiveButtonText = getString(R.string.alert_button_ok)
                            )
                        }
                        else -> {
                            okAlertDialog(
                                title = getString(R.string.error),
                                message = it.error,
                                positiveButtonText = getString(R.string.alert_button_ok)
                            )
                        }
                    }
                }
            }
        }
    }

    /*private fun launchHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }*/
}
