package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.domain.model.DestinationState
import com.example.assignment3_hansenbillyramades.data.repository.DestinationRepository
import com.example.assignment3_hansenbillyramades.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateItineraryViewModel @Inject constructor(
    private val repository: DestinationRepository,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _destinationState = MutableStateFlow<DestinationState>(DestinationState.Loading)
    val destinationState: StateFlow<DestinationState> = _destinationState

    suspend fun getToken(): String? {
        return loginUseCase.getToken()
    }

    fun getDestinationDetail(id: Int, token: String, name: String, type: String) {
        viewModelScope.launch {
            try {
                _destinationState.value = DestinationState.Loading
                val destinationList = repository.getDestinations(page = 1, token, name, type)
                val destinationDetail = destinationList.find { it.id == id }

                if (destinationDetail != null) {
                    _destinationState.value = DestinationState.SuccessDetail(destinationDetail)
                } else {
                    _destinationState.value = DestinationState.Error("Destination not found")
                }
            } catch (e: Exception) {
                _destinationState.value =
                    DestinationState.Error(e.message ?: "An error occurred")
            }
        }
    }
}