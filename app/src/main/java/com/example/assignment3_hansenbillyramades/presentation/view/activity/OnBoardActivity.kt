package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.assignment3_hansenbillyramades.databinding.ActivityOnBoardBinding
import com.example.assignment3_hansenbillyramades.presentation.adapter.OnBoardingPagerAdapter
import com.example.assignment3_hansenbillyramades.presentation.view.fragment.OnBoarding1Fragment
import com.example.assignment3_hansenbillyramades.presentation.view.fragment.OnBoarding2Fragment
import com.example.assignment3_hansenbillyramades.presentation.viewModel.OnBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnBoardBinding
    private val viewModel: OnBoardViewModel by viewModels()
    lateinit var pagerAdapter: OnBoardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        lifecycleScope.launch {
//            // Cek apakah sudah onboarded
//            if (viewModel.isOnboarded()) {
//                // Jika sudah onboarded, langsung ke halaman rekomendasi
//                val intent = Intent(this@OnBoardActivity, OnBoardActivity::class.java)
//                startActivity(intent)
//                finish() // Tutup OnBoardActivity
//            }
//        }

        pagerAdapter = OnBoardingPagerAdapter(this)
        pagerAdapter.addFragment(OnBoarding1Fragment())
        pagerAdapter.addFragment(OnBoarding2Fragment())

        binding.viewPager.adapter = pagerAdapter
        binding.dotsIndicator.setViewPager2(binding.viewPager)
    }
}


