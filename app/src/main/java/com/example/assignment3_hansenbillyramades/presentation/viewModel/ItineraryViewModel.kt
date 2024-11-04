package com.example.assignment3_hansenbillyramades.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity
import com.example.assignment3_hansenbillyramades.data.source.local.TravelMateDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(
    application: Application,
    private val db: TravelMateDatabase
) : AndroidViewModel(application) {

    private val _itineraryList = MutableLiveData<List<ItineraryEntity>>()
    val itineraryList: LiveData<List<ItineraryEntity>> get() = _itineraryList

    private val _isItineraryEmpty = MutableLiveData<Boolean>()
    val isItineraryEmpty: LiveData<Boolean> get() = _isItineraryEmpty

    init {
        loadItinerary()
    }

    fun loadItinerary() {
        viewModelScope.launch {
            val itinerary = db.itineraryDao().getItinerary()
            _itineraryList.value = itinerary
            _isItineraryEmpty.value = itinerary.isEmpty()
        }
    }

    fun deleteItinerary(itinerary: ItineraryEntity) {
        viewModelScope.launch {
            db.itineraryDao().deleteItinerary(itinerary)
            loadItinerary()
        }
    }
}
