package com.example.assignment3_hansenbillyramades.domain.repository

import android.util.Log
import com.example.assignment3_hansenbillyramades.data.model.LoginDto
import com.example.assignment3_hansenbillyramades.data.source.local.LocalDataSource
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.data.source.network.ApiService
import com.example.assignment3_hansenbillyramades.data.source.network.UserRemoteDataSource
import com.example.assignment3_hansenbillyramades.domain.model.Login
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import com.example.assignment3_hansenbillyramades.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val preferenceDataStore: PreferenceDataStore
) : UserRepository {

    override suspend fun login(request: LoginRequest): LoginDto {
        val loginDto = remoteDataSource.login(request)
        Log.d("UserRepositoryImpl", "Login Response: $loginDto")
        return loginDto
    }

    override suspend fun logout() {
        preferenceDataStore.clearUserData()
        preferenceDataStore.setOnboardedStatus(true)
    }

    override suspend fun getToken(): String? {
        return localDataSource.getToken()
    }

    override suspend fun setToken(setToken: String) {
        return localDataSource.setToken(setToken)
    }

    override suspend fun setUserDetails(user: User) {
        return localDataSource.setUserDetails(user)
    }

    override suspend fun getUserDetails(): User? {
        return localDataSource.getUserDetails()
    }
}

fun LoginDto.toLogin(): Login {
    return Login(
        token = this.data.token,
        user = User(
            firstName = this.data.firstName,
            lastName = this.data.lastName,
            email = this.data.email,
            phone = this.data.phone,
            avatar = this.data.avatar,
            token = this.data.token
        )
    )
}
