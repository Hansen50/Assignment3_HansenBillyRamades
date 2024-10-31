package com.example.assignment3_hansenbillyramades.data.repository

import com.example.assignment3_hansenbillyramades.data.model.LoginDto
import com.example.assignment3_hansenbillyramades.domain.model.Login
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import com.example.assignment3_hansenbillyramades.domain.model.User

interface UserRepository {
    suspend fun login(request: LoginRequest): LoginDto
    suspend fun logout()
    suspend fun getToken(): String?
    suspend fun setToken(setToken: String)
    suspend fun setUserDetails(user: User)
    suspend fun getUserDetails(): User?
}