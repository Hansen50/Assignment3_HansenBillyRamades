package com.example.assignment3_hansenbillyramades.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.assignment3_hansenbillyramades.databinding.FragmentOnBoarding2Binding
import com.example.assignment3_hansenbillyramades.presentation.view.activity.PreferencesTravelActivity
import com.example.assignment3_hansenbillyramades.presentation.viewModel.OnBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoarding2Fragment : Fragment() {

    private var _binding: FragmentOnBoarding2Binding? = null
    private val binding get() = _binding!!
    private lateinit var token: String

    private val viewModel: OnBoardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =
            FragmentOnBoarding2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Use the ViewModel

        lifecycleScope.launch {
            token = "Bearer ${viewModel.getToken()}"
        }

        binding.btnStart.setOnClickListener {
            val intent = Intent(requireContext(), PreferencesTravelActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}