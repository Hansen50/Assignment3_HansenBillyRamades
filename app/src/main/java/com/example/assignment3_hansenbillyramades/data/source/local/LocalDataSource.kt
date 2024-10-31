package com.example.assignment3_hansenbillyramades.data.source.local

import com.example.assignment3_hansenbillyramades.domain.model.User


interface LocalDataSource {
    suspend fun getToken(): String?
    suspend fun setToken(setToken: String)
    suspend fun setUserDetails(user: User)
    suspend fun getUserDetails(): User?

}