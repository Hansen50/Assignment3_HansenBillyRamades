package com.example.assignment3_hansenbillyramades.di

import android.app.Application
import android.content.Context
import com.example.assignment3_hansenbillyramades.data.source.local.LocalDataSource
import com.example.assignment3_hansenbillyramades.data.source.local.LocalDataSourceImpl
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.data.source.local.dataStore
import com.example.assignment3_hansenbillyramades.data.source.network.ApiService
import com.example.assignment3_hansenbillyramades.data.source.network.UserRemoteDataSource
import com.example.assignment3_hansenbillyramades.data.source.network.UserRemoteDataSourceImpl
import com.example.assignment3_hansenbillyramades.data.repository.DestinationRepository
import com.example.assignment3_hansenbillyramades.data.repository.DestinationRepositoryImpl
import com.example.assignment3_hansenbillyramades.data.repository.UserRepository
import com.example.assignment3_hansenbillyramades.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://167.99.74.195:8090/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(apiService: ApiService): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(preferenceDataStore: PreferenceDataStore): LocalDataSource {
        return LocalDataSourceImpl(preferenceDataStore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource: UserRemoteDataSource,
        localDataSource: LocalDataSource,
        preferenceDataStore: PreferenceDataStore
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource, localDataSource, preferenceDataStore)
    }

    @Provides
    @Singleton
    fun provideDestinationRepository(remoteDataSource: UserRemoteDataSource): DestinationRepository {
        return DestinationRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun providePreferenceDataStore(context: Context): PreferenceDataStore {
        return PreferenceDataStore.getInstance(context.dataStore)
    }
}
