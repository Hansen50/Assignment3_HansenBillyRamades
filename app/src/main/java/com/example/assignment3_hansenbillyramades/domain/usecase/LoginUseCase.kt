package com.example.assignment3_hansenbillyramades.domain.usecase

import com.example.assignment3_hansenbillyramades.data.model.LoginDto
import com.example.assignment3_hansenbillyramades.domain.model.Login
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import com.example.assignment3_hansenbillyramades.domain.model.User
import com.example.assignment3_hansenbillyramades.domain.repository.UserRepository
import com.example.assignment3_hansenbillyramades.domain.repository.toLogin
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(request: LoginRequest): Login {
        val loginDto = userRepository.login(request)
        return loginDto.toLogin()
    }

    suspend fun setToken(setToken: String) {
        return userRepository.setToken(setToken)
    }

    suspend fun getToken(): String? {
        return userRepository.getToken()

    }

    suspend fun setUserDetails(user: User) {
        return userRepository.setUserDetails(user)
    }

    suspend fun getUserDetails(): User? {
        return userRepository.getUserDetails()
    }
}
