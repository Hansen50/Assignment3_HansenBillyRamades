package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val preferenceDataStore: PreferenceDataStore) : ViewModel() {

    private val _userToken = MutableLiveData<String?>()
    val userToken: LiveData<String?> get() = _userToken

    fun fetchUserToken() {
        viewModelScope.launch {
            try {
                val userDetails = preferenceDataStore.getUserDetails()
                _userToken.value = userDetails?.token
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
