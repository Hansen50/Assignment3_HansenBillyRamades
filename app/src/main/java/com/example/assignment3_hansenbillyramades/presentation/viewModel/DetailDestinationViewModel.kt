package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.domain.model.DestinationState
import com.example.assignment3_hansenbillyramades.domain.model.Destinations
import com.example.assignment3_hansenbillyramades.domain.repository.DestinationRepository
import com.example.assignment3_hansenbillyramades.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailDestinationViewModel @Inject constructor(
    private val repository: DestinationRepository,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _destinationState = MutableStateFlow<DestinationState>(DestinationState.Loading)
    val destinationState: StateFlow<DestinationState> = _destinationState

    suspend fun getToken(): String? {
        return loginUseCase.getToken()
    }

    fun getDestinationDetail(id: Int, token: String, name: String?, type: String?) {
        viewModelScope.launch {
            _destinationState.value = DestinationState.Loading
            var page = 1
            var destinationDetail: Destinations? = null

            try {
                while (destinationDetail == null) {
                    val destinationList = repository.getDestinations(page, token, name, type)
                    destinationDetail = destinationList.find { it.id == id }
                    page++
                }

                _destinationState.value = DestinationState.SuccessDetail(destinationDetail)

            } catch (e: Exception) {
                _destinationState.value = DestinationState.Error(e.message ?: "An error occurred")
            }
        }
    }
}

