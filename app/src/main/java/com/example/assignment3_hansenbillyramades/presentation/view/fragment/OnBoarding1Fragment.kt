package com.example.assignment3_hansenbillyramades.presentation.view.fragment

import com.example.assignment3_hansenbillyramades.presentation.view.activity.OnBoardActivity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.assignment3_hansenbillyramades.databinding.FragmentOnBoarding1Binding
import com.example.assignment3_hansenbillyramades.presentation.view.activity.MainActivity
import com.example.assignment3_hansenbillyramades.presentation.viewModel.OnBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoarding1Fragment : Fragment() {
    private var _binding: FragmentOnBoarding1Binding? = null
    private val binding get() = _binding!!

    private val viewModel: OnBoardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val token = "Bearer ${viewModel.getToken()}"
            // Gunakan token sesuai kebutuhan di sini
        }

        binding.btnNext.setOnClickListener {
            navigateToNextPage()
        }

        binding.btnSkip.setOnClickListener {
            skipOnboarding()
        }
    }

    private fun navigateToNextPage() {
        val currentItem = (activity as OnBoardActivity).binding.viewPager.currentItem
        if (currentItem < (activity as OnBoardActivity).pagerAdapter.itemCount - 1) {
            (activity as OnBoardActivity).binding.viewPager.currentItem = currentItem + 1
        }
    }

    private fun skipOnboarding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setOnboarded(true)
        }
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





