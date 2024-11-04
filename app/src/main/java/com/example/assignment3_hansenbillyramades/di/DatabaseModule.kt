package com.example.assignment3_hansenbillyramades.di

import android.content.Context
import androidx.room.Room
import com.example.assignment3_hansenbillyramades.data.source.local.TravelMateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(app: Context): TravelMateDatabase {
        return Room.databaseBuilder(
            app,
            TravelMateDatabase::class.java,
            "travelmate_database"
        ).build()
    }
}
