package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferenceDataStore: PreferenceDataStore
) : ViewModel() {


    suspend fun getToken(): String? {
        return loginUseCase.getToken()
    }

    suspend fun setOnboarded(onboarded: Boolean) {
        preferenceDataStore.setOnboardedStatus(onboarded)
    }

    suspend fun isOnboarded(): Boolean {
        return preferenceDataStore.isUserOnboarded()
    }
}
