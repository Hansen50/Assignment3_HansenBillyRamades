package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.domain.model.DestinationState
import com.example.assignment3_hansenbillyramades.domain.model.UserState
import com.example.assignment3_hansenbillyramades.domain.usecase.GetListDestinationsUseCase
import com.example.assignment3_hansenbillyramades.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getListDestinationsUseCase: GetListDestinationsUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _destinationState = MutableStateFlow<DestinationState>(DestinationState.Loading)
    val destinationState: StateFlow<DestinationState> = _destinationState.asStateFlow()

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    suspend fun getToken(): String? {
        return loginUseCase.getToken()
    }

    suspend fun getUserDetails() {
        try {
            val profile = loginUseCase.getUserDetails()
            _userState.value = UserState.Success(profile)
        } catch (e: Exception) {
            _userState.value = UserState.Error(e.message ?: "Unknown Error")
        }
    }

    fun loadDestinations(page: Int, token: String, name: String, type: String) {
        if (page <= 50) {
            viewModelScope.launch {
                _destinationState.value = DestinationState.Loading
                try {
                    val destinations = getListDestinationsUseCase(page, token, name, type)
                    _destinationState.value = DestinationState.Success(destinations)
                } catch (e: Exception) {
                    _destinationState.value = DestinationState.Error(e.message ?: "Unknown Error")
                }
            }
        }
    }
}
