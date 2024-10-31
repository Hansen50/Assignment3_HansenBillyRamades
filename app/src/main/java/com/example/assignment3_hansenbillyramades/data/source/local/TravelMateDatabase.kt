package com.example.assignment3_hansenbillyramades.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItineraryEntity::class], version = 1, exportSchema = false)
abstract class TravelMateDatabase : RoomDatabase() {
    abstract fun itineraryDao() : ItineraryDao

    companion object {
        private var INSTANCE: TravelMateDatabase? = null

        fun getDatabase(context: Context) : TravelMateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelMateDatabase::class.java,
                    "travelmate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}