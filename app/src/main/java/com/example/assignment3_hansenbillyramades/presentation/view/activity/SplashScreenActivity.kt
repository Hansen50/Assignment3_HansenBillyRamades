package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.assignment3_hansenbillyramades.data.source.local.dataStore
import com.example.assignment3_hansenbillyramades.databinding.ActivitySplashScreenBinding
import com.example.assignment3_hansenbillyramades.presentation.viewModel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val splashDelay = 500L
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            splashScreenViewModel.fetchUserToken()
        }, splashDelay)

        splashScreenViewModel.userToken.observe(this, Observer { token ->
            val intent = if (token != null) {
                Intent(this, MainActivity::class.java).apply {
                    putExtra("TOKEN", token)
                }
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        })
    }
}
