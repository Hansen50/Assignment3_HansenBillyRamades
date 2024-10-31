package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.domain.model.DestinationState
import com.example.assignment3_hansenbillyramades.domain.model.UserState
import com.example.assignment3_hansenbillyramades.data.repository.UserRepository
import com.example.assignment3_hansenbillyramades.domain.usecase.GetListDestinationsUseCase
import com.example.assignment3_hansenbillyramades.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    suspend fun getUserDetails()  {
        try {
            val profile = loginUseCase.getUserDetails()
            _userState.value = UserState.Success(profile)

        } catch (e: Exception) {
            _userState.value = UserState.Error(e.message ?: "Unknown Error")
        }
    }

     fun logOut() {
        viewModelScope.launch {
            userRepository.logout()
            _userState.value = UserState.Logout
        }
    }
}