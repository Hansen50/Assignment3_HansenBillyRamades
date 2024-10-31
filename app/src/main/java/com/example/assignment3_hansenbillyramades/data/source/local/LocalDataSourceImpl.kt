package com.example.assignment3_hansenbillyramades.data.source.local

import com.example.assignment3_hansenbillyramades.domain.model.User
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val dataStore: PreferenceDataStore) :
LocalDataSource {
    override suspend fun getToken(): String? {
        return  dataStore.getToken()
    }

    override suspend fun setToken(setToken: String) {
        return dataStore.setToken(setToken)
    }

    override suspend fun setUserDetails(user: User) {
        return dataStore.setUserDetails(user)
    }

    override suspend fun getUserDetails(): User? {
        return dataStore.getUserDetails()
    }
}