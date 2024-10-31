package com.example.assignment3_hansenbillyramades.data.repository

import android.util.Log
import com.example.assignment3_hansenbillyramades.data.model.DestinationsDto
import com.example.assignment3_hansenbillyramades.data.source.network.ApiService
import com.example.assignment3_hansenbillyramades.data.source.network.UserRemoteDataSource
import com.example.assignment3_hansenbillyramades.domain.model.Destinations
import javax.inject.Inject

class DestinationRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : DestinationRepository {

    override suspend fun getDestinations(page: Int, token: String, name: String, type: String): List<Destinations> {
        return remoteDataSource.getDestinations(page, token, name, type).data?.mapNotNull { it?.toDestination() } ?: emptyList()
    }
}


fun DestinationsDto.Data.toDestination(): Destinations {
    return Destinations(
        id = this.id ?: 0,
        name = this.name ?: "",
        description = this.description ?: "",
        duration = this.duration ?: "",
        image = this.image ?: "",
        location = this.location ?: "",
        activity = this.activity ?: "",
        popularity = this.popularity ?: "",
        type = this.type ?: ""
    )
}


