package com.mufti.test.storeapps.data

import android.util.Log
import com.mufti.test.storeapps.data.local.datastore.DataStorePreferences
import com.mufti.test.storeapps.data.local.datastore.LoggedInUser
import com.mufti.test.storeapps.data.local.room.dao.ProductDao
import com.mufti.test.storeapps.data.remote.mapper.ProductMapper
import com.mufti.test.storeapps.data.remote.request.auth.LoginRequest
import com.mufti.test.storeapps.data.remote.retrofit.ApiService
import com.mufti.test.storeapps.domain.model.Product
import com.mufti.test.storeapps.utils.constant.CategoryProductType
import com.mufti.test.storeapps.utils.filter.FilterUtils
import retrofit2.HttpException

class StoreRepository private constructor(
    private val apiService: ApiService,
    private val dataStore: DataStorePreferences,
    private val productDao: ProductDao,
) {
    suspend fun login(
        request: LoginRequest
    ): Result<Boolean> {
        return try {
            val response = apiService.login(request)
            val result = response.token != null

            val userLogin = LoggedInUser(request.username, result)
            dataStore.saveLoginUser(userLogin)
            Result.Success(result)
        } catch (e: HttpException) {
            Log.d("StoryRepositoryImpl", "login: ${e.message.toString()} ")
            Result.Error(code = e.code(), error = e.message.toString())
        } catch (e: Exception) {
            Log.d("StoreRepository", "login: ${e.message.toString()} ")
            Result.Error(error = e.message.toString())
        }
    }

    fun getLoginUser() = dataStore.getLoginUser()

    suspend fun setLoginUser(username: String = "", isLogin: Boolean) {
        val userLogin = LoggedInUser(username, isLogin)
        dataStore.saveLoginUser(userLogin)
    }

    suspend fun getListProduct(): Result<List<Product>> {
        return try {
            val response = apiService.getListProduct()
            val dataResult = ProductMapper.mapListProductResponseToListProductEntity(response)

            productDao.deleteAllProduct()

            productDao.insertProduct(dataResult)

            Result.Success(ProductMapper.mapListProductEntityToListProduct(productDao.getAllProduct()))
        } catch (e: HttpException) {
            Log.d("StoreRepository", "getListProduct: ${e.message.toString()} ")
            Result.Error(code = e.code(), error = e.message.toString())
        } catch (e: Exception) {
            Log.d("StoreRepository", "getListProduct: ${e.message.toString()} ")
            Result.Error(error = e.message.toString())
        }
    }

    fun getFilterProduct(categoryType: CategoryProductType): Result<List<Product>> {
        return try {
            val query = FilterUtils.getCategoryQuery(categoryType)
            val dataResult = productDao.getFilterProduct(query)
            Result.Success(ProductMapper.mapListProductEntityToListProduct(dataResult))
        } catch (e: Exception) {
            Log.d("StoreRepository", "getFilterProduct: ${e.message.toString()} ")
            Result.Error(error = e.message.toString())
        }
    }


    suspend fun getDetailProduct(
        idProduct: Int
    ): Result<Product> {
        return try {
            val response = apiService.getDetailProduct(idProduct)
            val dataResult = ProductMapper.mapProductResponseToProduct(response)

            Result.Success(dataResult)
        } catch (e: HttpException) {
            Log.d("StoreRepository", "getListProduct: ${e.message.toString()} ")
            Result.Error(code = e.code(), error = e.message.toString())
        } catch (e: Exception) {
            Log.d("StoreRepository", "getListProduct: ${e.message.toString()} ")
            Result.Error(error = e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoreRepository? = null

        fun getInstance(
            apiService: ApiService,
            dataStore: DataStorePreferences,
            productDao: ProductDao
        ): StoreRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = StoreRepository(apiService, dataStore, productDao)
                INSTANCE = instance
                instance
            }
        }
    }
}
