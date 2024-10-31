package com.example.assignment3_hansenbillyramades.domain.model

sealed class DestinationState {
    data object Loading: DestinationState()
    data class Success(val destinations: List<Destinations>) : DestinationState()
    data class SuccessDetail(val destination: Destinations) : DestinationState()
    data class Error(val message: String) : DestinationState()
}