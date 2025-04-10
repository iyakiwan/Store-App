package com.mufti.test.storeapps.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val loginKey = booleanPreferencesKey("login_key")
    private val usernameKey = stringPreferencesKey("username_key")

    fun getLoginUser(): Flow<LoggedInUser> = dataStore.data.map { preferences ->
        LoggedInUser(
            userName = preferences[usernameKey] ?: "",
            isLogin = preferences[loginKey] ?: false
        )
    }

    suspend fun saveLoginUser(loginUser: LoggedInUser) =
        dataStore.edit { preferences ->
            preferences[loginKey] = loginUser.isLogin
            preferences[usernameKey] = loginUser.userName
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
