package com.example.assignment3_hansenbillyramades.data.source.network

import com.example.assignment3_hansenbillyramades.data.model.DestinationsDto
import com.example.assignment3_hansenbillyramades.data.model.LoginDto
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import com.example.assignment3_hansenbillyramades.domain.model.Login
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    UserRemoteDataSource {
    override suspend fun login(request: LoginRequest): LoginDto {
        return apiService.login(request)
    }



    override suspend fun getDestinations(page: Int, token: String, name: String?, type: String?): DestinationsDto {
        return apiService.getDestinations(page, token, name, type)
    }

    override suspend fun getAllDestinations(page: Int, token: String): DestinationsDto {
        return apiService.getAllDestinations(page, token)
    }
}