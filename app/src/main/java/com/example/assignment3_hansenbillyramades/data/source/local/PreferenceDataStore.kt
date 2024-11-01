package com.example.assignment3_hansenbillyramades.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.assignment3_hansenbillyramades.domain.model.User
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

object DataStoreConstant {
    val FIRST_NAME = stringPreferencesKey("FIRST_NAME")
    val LAST_NAME = stringPreferencesKey("LAST_NAME")
    val EMAIL = stringPreferencesKey("EMAIL")
    val PHONE = stringPreferencesKey("PHONE")
    val AVATAR = stringPreferencesKey("AVATAR")
    val TOKEN = stringPreferencesKey("TOKEN")
    val IS_LOGIN = booleanPreferencesKey("IS_LOGIN")
    val SELECTED_RECOMMENDATION_TYPE = stringPreferencesKey("SELECTED_RECOMMENDATION_TYPE")
    val IS_SELECTED_RECOMMENDATION = booleanPreferencesKey("IS_SELECTED_RECOMMENDATION")
    val IS_ONBOARDED = booleanPreferencesKey("IS_ONBOARDED")
}

class PreferenceDataStore private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun setUserDetails(user: User) {
        dataStore.edit { preferences ->
            preferences[DataStoreConstant.FIRST_NAME] = user.firstName
            preferences[DataStoreConstant.LAST_NAME] = user.lastName
            preferences[DataStoreConstant.EMAIL] = user.email
            preferences[DataStoreConstant.PHONE] = user.phone
            preferences[DataStoreConstant.AVATAR] = user.avatar
            preferences[DataStoreConstant.TOKEN] = user.token
            preferences[DataStoreConstant.IS_LOGIN] = true
        }
    }

    suspend fun setToken(setToken: String) {
        dataStore.edit { preferences -> preferences[DataStoreConstant.TOKEN] = setToken }
    }


    // menyimpan user telah memilih
    suspend fun setRecommendationSelected(selected: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreConstant.IS_SELECTED_RECOMMENDATION] = selected
        }
    }

    suspend fun setOnboardedStatus(onboarded: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreConstant.IS_ONBOARDED] = onboarded
        }
    }

    // Menyimpan tipe rekomendasi yang dipilih ke data store
    suspend fun setSelectedRecommendationType(type: String) {
        dataStore.edit { preferences ->
            preferences[DataStoreConstant.SELECTED_RECOMMENDATION_TYPE] = type
        }
    }

    // Mengambil tipe rekomendasi yang dipilih
    suspend fun getSelectedRecommendationType(): String? {
        return dataStore.data.first()[DataStoreConstant.SELECTED_RECOMMENDATION_TYPE]
    }

    suspend fun getToken(): String? {
        return dataStore.data.first()[DataStoreConstant.TOKEN]
    }

    suspend fun getUserDetails(): User? {
        val preferences = dataStore.data.first()
        return if (preferences[DataStoreConstant.IS_LOGIN] == true) {
            User(
                firstName = preferences[DataStoreConstant.FIRST_NAME] ?: "",
                lastName = preferences[DataStoreConstant.LAST_NAME] ?: "",
                email = preferences[DataStoreConstant.EMAIL] ?: "",
                phone = preferences[DataStoreConstant.PHONE] ?: "",
                avatar = preferences[DataStoreConstant.AVATAR] ?: "",
                token = ""
            )
        } else {
            null
        }
    }

    suspend fun isUserOnboarded(): Boolean {
        return dataStore.data.first()[DataStoreConstant.IS_ONBOARDED] ?: false
    }

    // nge cek apa sudah memilih rekomen
    suspend fun hasSelectedRecommendation(): Boolean {
        return dataStore.data.first()[DataStoreConstant.IS_SELECTED_RECOMMENDATION] ?: false
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences -> preferences.clear() }
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferenceDataStore? = null

        fun getInstance(dataStore: DataStore<Preferences>): PreferenceDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferenceDataStore(dataStore).also { INSTANCE = it }
            }
        }
    }
}
