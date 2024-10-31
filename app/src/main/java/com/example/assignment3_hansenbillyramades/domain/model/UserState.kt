package com.example.assignment3_hansenbillyramades.domain.model

sealed class UserState {
    data object Loading: UserState()
    data class Success(val user: User?) : UserState()
    data class Error (val message : String) : UserState()
    data object Logout : UserState()
}