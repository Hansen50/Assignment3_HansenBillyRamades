package com.example.assignment3_hansenbillyramades.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.databinding.ActivityLoginBinding
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import com.example.assignment3_hansenbillyramades.domain.model.LoginState
import com.example.assignment3_hansenbillyramades.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    // Menyuntikkan PreferenceDataStore langsung ke Activity
    @Inject
    lateinit var preferenceDataStore: PreferenceDataStore

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUsers(LoginRequest(email, password))
            } else {
                Toast.makeText(this, "You have to fill email and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Success -> {
                        val token = state.login.token
                        viewModel.setToken(token)
                        val user = state.login.user
                        viewModel.setUserDetails(user)

                        // Periksa status onboarded sebelum menentukan navigasi
                        val isOnboarded =
                            preferenceDataStore.isUserOnboarded() // Dapatkan status onboarded
                        val intent = if (isOnboarded) {
                            Intent(this@LoginActivity, MainActivity::class.java)
                        } else {
                            Intent(this@LoginActivity, OnBoardActivity::class.java)
                        }

                        intent.putExtra("TOKEN", token)
                        startActivity(intent)
                        finish()
                    }

                    is LoginState.Error -> {
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }

                    is LoginState.Loading -> {
                        // Tampilkan loading jika perlu
                    }
                }
            }
        }
    }
}





