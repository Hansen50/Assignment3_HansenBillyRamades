package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment3_hansenbillyramades.R
import com.example.assignment3_hansenbillyramades.databinding.ActivityPreferencesTravelBinding
import com.example.assignment3_hansenbillyramades.presentation.viewModel.PreferencesTravelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesTravelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreferencesTravelBinding
    private val viewModel: PreferencesTravelViewModel by viewModels()
    private var selectedChipId: Int = R.id.chip1

    companion object {
        const val EXTRA_SELECTED_TYPE = "selected_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesTravelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fromProfile = intent.getBooleanExtra("FROM_PROFILE", false)
        if (fromProfile) {
            binding.tvH1.text = "Change Your Preference"
            binding.nextButton.text = "Save"
            binding.ivIconNoTextPref.setImageResource(R.drawable.baseline_chevron_left_32)

            binding.ivIconNoTextPref.setOnClickListener {
                // Kembali ke halaman sebelumnya
                onBackPressedDispatcher.onBackPressed()
            }


        }


        var selectedType: String? = null

//        viewModel.checkIfRecommendationSelected { hasSelected ->
//            if (hasSelected) {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedChip = findViewById<com.google.android.material.chip.Chip>(checkedId)
            selectedChip?.let {
                selectedType = it.text.toString()
                viewModel.saveSelectedRecommendationType(selectedType!!)
                selectedChipId = checkedId
            }
        }


        binding.nextButton.setOnClickListener {
            selectedType?.let { type ->
                viewModel.saveSelectedRecommendationType(type)
                val intent = Intent(this@PreferencesTravelActivity, MainActivity::class.java).apply {
                    putExtra(EXTRA_SELECTED_TYPE, type)
                }
                startActivity(intent)
                finish()
            }
        }
    }
}
