package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.data.source.local.dataStore
import com.example.assignment3_hansenbillyramades.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val splashDelay = 1500L
    private lateinit var preferenceDataStore: PreferenceDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceDataStore = PreferenceDataStore.getInstance(dataStore)

        Handler(Looper.getMainLooper()).postDelayed({
            getTokenFromDataStore()
        }, splashDelay)
    }

    private fun getTokenFromDataStore() {
        lifecycleScope.launch {
            try {
                val userDetails = preferenceDataStore.getUserDetails()
                val token = userDetails?.token

                val intent = if (token != null) {
                    Intent(this@SplashScreenActivity, MainActivity::class.java).apply {
                        putExtra("TOKEN", token)
                    }
                } else {
                    Intent(this@SplashScreenActivity, LoginActivity::class.java)
                }
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                e.printStackTrace() // Menangani error
            }
        }
    }
}
