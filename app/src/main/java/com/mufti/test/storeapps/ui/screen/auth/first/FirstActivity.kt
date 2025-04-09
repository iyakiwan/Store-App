package com.mufti.test.storeapps.ui.screen.auth.first

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mufti.test.storeapps.R
import com.mufti.test.storeapps.databinding.ActivityFirstBinding
import com.mufti.test.storeapps.ui.screen.auth.login.LoginActivity

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindow()

        showSplashScreen()
    }

    private fun setupWindow() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            /*if (viewModel.getIsLogin()) {
                startActivity(Intent(this@FirstActivity, HomeActivity::class.java))
            } else {*/
                startActivity(Intent(this@FirstActivity, LoginActivity::class.java))
//            }
            finish()
        }, SPLASH_TIME_OUT)
    }

    companion object {
        const val SPLASH_TIME_OUT = 1500L
    }
}