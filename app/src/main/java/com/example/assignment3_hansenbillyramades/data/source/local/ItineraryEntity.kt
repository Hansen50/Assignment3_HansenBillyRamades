package com.example.assignment3_hansenbillyramades.data.source.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "itinerary_entity")
data class ItineraryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "destination_id") val destinationId: Int,
    @ColumnInfo(name = "destination_name") val destinationName: String,
    @ColumnInfo(name = "city_name") val cityName: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "rating") val rating: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "activities") val activities: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "note") var note: String
) : Parcelable
