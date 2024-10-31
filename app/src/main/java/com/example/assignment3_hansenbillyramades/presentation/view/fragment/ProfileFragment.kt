package com.example.assignment3_hansenbillyramades.presentation.view.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.assignment3_hansenbillyramades.databinding.FragmentProfileBinding
import com.example.assignment3_hansenbillyramades.domain.model.UserState
import com.example.assignment3_hansenbillyramades.presentation.view.activity.LoginActivity
import com.example.assignment3_hansenbillyramades.presentation.view.activity.PreferencesTravelActivity
import com.example.assignment3_hansenbillyramades.presentation.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardMenuChangePreference.setOnClickListener {
            val intent = Intent(requireContext(), PreferencesTravelActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logOut()
        }

        lifecycleScope.launch {
            viewModel.getUserDetails()
        }

        lifecycleScope.launch {
            viewModel.userState.collect(object : FlowCollector<UserState> {
                override suspend fun emit(value: UserState) {
                    when (value) {
                        is UserState.Success -> {
                            binding.tvName.text = value.user?.firstName
                            binding.tvEmail.text = value.user?.email
                            binding.tvNumberPhone.text = value.user?.phone

                            Glide.with(requireContext())
                                .load(value.user?.avatar)
                                .circleCrop()
                                .into(binding.ivProfile)
                        }

                        is UserState.Error -> {
                            isLoading = false
                            Toast.makeText(requireContext(), value.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is UserState.Loading -> {
                            isLoading = true
                        }

                        UserState.Logout -> {
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                            activity?.finish()
                        }
                    }
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}