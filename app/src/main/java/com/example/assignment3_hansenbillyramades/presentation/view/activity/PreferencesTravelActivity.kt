package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment3_hansenbillyramades.databinding.ActivityPreferencesTravelBinding

class PreferencesTravelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreferencesTravelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesTravelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            val intent = Intent(this@PreferencesTravelActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

