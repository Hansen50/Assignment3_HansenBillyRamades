package com.example.assignment3_hansenbillyramades.data.source.network

import com.example.assignment3_hansenbillyramades.data.model.DestinationsDto
import com.example.assignment3_hansenbillyramades.data.model.LoginDto
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/login")
    suspend fun login(@Body request: LoginRequest): LoginDto


    @GET("/travel")
    suspend fun getDestinations(
        @Query("page") page: Int,
        @Header("Authorization") token: String,
        @Query("name") name: String?,
        @Query("type") type: String?
    ): DestinationsDto


    @GET("/travel")
    suspend fun getAllDestinations(
        @Query("page") page: Int,
        @Header("Authorization") token: String,
    ): DestinationsDto

}




