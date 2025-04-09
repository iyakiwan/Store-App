package com.mufti.test.storeapps.data

import android.util.Log
import com.mufti.test.storeapps.data.local.datastore.DataStorePreferences
import com.mufti.test.storeapps.data.remote.request.auth.LoginRequest
import com.mufti.test.storeapps.data.remote.retrofit.ApiService
import retrofit2.HttpException

class StoreRepository private constructor(
    private val apiService: ApiService,
    private val dataStore: DataStorePreferences,
) {
    suspend fun login(
        request: LoginRequest
    ): Result<Boolean> {
        return try {
            val response = apiService.login(request)
            val result = response.token != null

            if (result) {
                dataStore.saveTokenUser(tokenUser = response.token.toString())
                Result.Success(true)
            } else {
                Result.Success(false)
            }
        } catch (e: HttpException) {
            Log.d("StoryRepositoryImpl", "login: ${e.message.toString()} ")
            Result.Error(code = e.code(), error = e.message.toString())
        }  catch (e: Exception) {
            Log.d("StoreRepository", "login: ${e.message.toString()} ")
            Result.Error(error = e.message.toString())
        }
    }

    fun getTokenUser() = dataStore.getTokenUser()

    /*fun getLoginUser() = dataStore.getLoginUser()*/

    companion object {
        @Volatile
        private var INSTANCE: StoreRepository? = null

        fun getInstance(
            apiService: ApiService,
            dataStore: DataStorePreferences,
        ): StoreRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = StoreRepository(apiService, dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
