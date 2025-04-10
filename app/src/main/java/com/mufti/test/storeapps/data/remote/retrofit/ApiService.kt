package com.mufti.test.storeapps.data.remote.retrofit

import com.mufti.test.storeapps.data.remote.request.auth.LoginRequest
import com.mufti.test.storeapps.data.remote.request.product.ProductResponse
import com.mufti.test.storeapps.data.remote.response.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("/products")
    suspend fun getListProduct(): List<ProductResponse>

    @GET("/products/{id}")
    suspend fun getDetailProduct(@Path("id") id: Int): ProductResponse
}
