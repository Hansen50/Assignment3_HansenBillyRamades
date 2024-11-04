package com.example.assignment3_hansenbillyramades.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesTravelViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
) : ViewModel() {

    // Menyimpan tipe rekomendasi yang dipilih
    fun saveSelectedRecommendationType(type: String) {
        viewModelScope.launch {
            preferenceDataStore.setRecommendationSelected(true)
            preferenceDataStore.setSelectedRecommendationType(type)
        }
    }

    fun getSelectedRecommendationType(): LiveData<String?> = liveData {
        emit(preferenceDataStore.getSelectedRecommendationType())
    }

    // Memeriksa apakah rekomendasi telah dipilih
    fun checkIfRecommendationSelected(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val hasSelected = preferenceDataStore.hasSelectedRecommendation()
            onResult(hasSelected)
        }
    }
}
