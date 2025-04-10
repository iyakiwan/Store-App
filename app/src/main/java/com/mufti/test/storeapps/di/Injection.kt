package com.mufti.test.storeapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mufti.test.storeapps.data.StoreRepository
import com.mufti.test.storeapps.data.local.datastore.DataStorePreferences
import com.mufti.test.storeapps.data.local.room.StoreDatabase
import com.mufti.test.storeapps.data.remote.retrofit.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideRepository(context: Context): StoreRepository {
        val apiService = ApiConfig.getApiService(context)
        val dataStore = DataStorePreferences.getInstance(context.dataStore)
        val database = StoreDatabase.getInstance(context)
        val productDao = database.productDao()
        return StoreRepository.getInstance(
            apiService = apiService,
            dataStore = dataStore,
            productDao = productDao,
        )
    }
}
