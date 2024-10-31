package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import com.example.assignment3_hansenbillyramades.domain.model.LoginState
import com.example.assignment3_hansenbillyramades.domain.model.User
import com.example.assignment3_hansenbillyramades.data.repository.toLogin
import com.example.assignment3_hansenbillyramades.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferenceDataStore: PreferenceDataStore,
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    suspend fun setToken(setToken: String) {
        loginUseCase.setToken(setToken)
    }

    suspend fun setUserDetails(user: User) {
        loginUseCase.setUserDetails(user)
    }

    fun loginUsers(request: LoginRequest) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val login = loginUseCase(request)
                _loginState.value = LoginState.Success(login)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Login failed")
            }
        }
    }
}


