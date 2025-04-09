package com.mufti.test.storeapps.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val tokenKey = stringPreferencesKey("token_user")
    private val loginKey = booleanPreferencesKey("login_user")


    fun getTokenUser(): Flow<String> = dataStore.data.map { preferences ->
        preferences[tokenKey] ?: ""
    }

    fun getLoginUser(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[loginKey] ?: false
    }


    suspend fun saveTokenUser(tokenUser: String) =
        dataStore.edit { preferences ->
            preferences[tokenKey] = tokenUser
        }


    companion object {
        @Volatile
        private var INSTANCE: DataStorePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): DataStorePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStorePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
