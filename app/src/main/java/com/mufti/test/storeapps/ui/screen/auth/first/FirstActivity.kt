package com.mufti.test.storeapps.ui.screen.auth.first

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.mufti.test.storeapps.databinding.ActivityFirstBinding
import com.mufti.test.storeapps.ui.screen.auth.login.LoginActivity
import com.mufti.test.storeapps.ui.screen.home.HomeActivity
import com.mufti.test.storeapps.utils.ViewModelFactory

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    private lateinit var viewModel: FirstViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindow()
        setupViewModel()

        showSplashScreen()
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
        )[FirstViewModel::class.java]
    }

    private fun showSplashScreen() {
        viewModel.getLoginUser().observe(this) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (it.isLogin) {
                    startActivity(Intent(this@FirstActivity, HomeActivity::class.java))
                } else {
                    startActivity(Intent(this@FirstActivity, LoginActivity::class.java))
                }
                finish()
            }, SPLASH_TIME_OUT)
        }

    }

    companion object {
        const val SPLASH_TIME_OUT = 1500L
    }
}