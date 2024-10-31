package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment3_hansenbillyramades.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _currentStep = MutableLiveData(1)


    suspend fun getToken(): String? {
        return loginUseCase.getToken()
    }

    }
