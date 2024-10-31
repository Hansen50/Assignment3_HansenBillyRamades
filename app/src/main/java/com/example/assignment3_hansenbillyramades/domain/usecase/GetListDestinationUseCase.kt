package com.example.assignment3_hansenbillyramades.domain.usecase

import com.example.assignment3_hansenbillyramades.domain.model.Destinations
import com.example.assignment3_hansenbillyramades.data.repository.DestinationRepository
import javax.inject.Inject

class GetListDestinationsUseCase @Inject constructor(
    private val destinationRepository: DestinationRepository
) {
    suspend operator fun invoke(page: Int, token: String, name: String, type: String): List<Destinations> {
        return destinationRepository.getDestinations(page, token, name, type)
    }
}
