package com.mufti.test.storeapps.data.remote.retrofit

import com.mufti.test.storeapps.data.remote.request.auth.LoginRequest
import com.mufti.test.storeapps.data.remote.response.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
