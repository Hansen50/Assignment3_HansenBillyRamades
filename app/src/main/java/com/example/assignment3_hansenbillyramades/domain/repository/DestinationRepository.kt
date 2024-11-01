package com.example.assignment3_hansenbillyramades.domain.repository

import com.example.assignment3_hansenbillyramades.domain.model.Destinations

interface DestinationRepository {
    suspend fun getDestinations(page: Int, token: String, name: String?, type: String?): List<Destinations>
    suspend fun getAllDestinations(page: Int, token: String): List<Destinations>
}