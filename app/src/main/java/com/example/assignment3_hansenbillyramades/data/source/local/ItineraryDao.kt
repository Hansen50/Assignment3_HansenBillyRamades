package com.example.assignment3_hansenbillyramades.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItineraryDao {

    @Insert
    suspend fun addItinerary(itineraryEntity: ItineraryEntity)

    @Query("SELECT * FROM itinerary_entity")
    suspend fun getItinerary(): List<ItineraryEntity>

    @Update
    suspend fun updateItinerary(itineraryEntity: ItineraryEntity)

    @Delete
    suspend fun deleteItinerary(itineraryEntity: ItineraryEntity)

}