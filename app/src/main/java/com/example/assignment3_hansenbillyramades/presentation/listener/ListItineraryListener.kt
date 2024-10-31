package com.example.assignment3_hansenbillyramades.presentation.listener

import com.example.assignment3_hansenbillyramades.data.source.local.ItineraryEntity

interface ListItineraryListener {
    fun onClick(itinerary: ItineraryEntity)
}