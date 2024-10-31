package com.example.assignment3_hansenbillyramades.data.repository

import com.example.assignment3_hansenbillyramades.domain.model.Destinations

interface DestinationRepository {
    suspend fun getDestinations(page: Int, token: String, name: String, type: String): List<Destinations>
}