package com.example.assignment3_hansenbillyramades.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity
import com.example.assignment3_hansenbillyramades.data.source.local.TravelMateDatabase
import kotlinx.coroutines.launch

class ItineraryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = TravelMateDatabase.getDatabase(application)

    private val _itineraryList = MutableLiveData<List<ItineraryEntity>>()
    val itineraryList: LiveData<List<ItineraryEntity>> get() = _itineraryList

    private val _isItineraryEmpty = MutableLiveData<Boolean>()
    val isItineraryEmpty: LiveData<Boolean> get() = _isItineraryEmpty

    fun loadItinerary() {
        viewModelScope.launch {
            val itinerary = db.itineraryDao().getItinerary()
            _itineraryList.value = itinerary
            _isItineraryEmpty.value = itinerary.isEmpty()
        }
    }
}
