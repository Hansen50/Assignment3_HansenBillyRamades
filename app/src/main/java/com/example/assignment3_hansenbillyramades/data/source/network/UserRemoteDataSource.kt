package com.example.assignment3_hansenbillyramades.data.source.network

import com.example.assignment3_hansenbillyramades.data.model.DestinationsDto
import com.example.assignment3_hansenbillyramades.data.model.LoginDto
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest

interface UserRemoteDataSource {
    suspend fun login(request: LoginRequest): LoginDto
    suspend fun getDestinations(page: Int, token: String, name: String? = null, type: String? = null): DestinationsDto
    suspend fun getAllDestinations(page: Int, token: String): DestinationsDto
}
