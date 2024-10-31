package com.example.assignment3_hansenbillyramades.domain.model

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val login: Login) : LoginState()
    data class Error(val message: String) : LoginState()
}
