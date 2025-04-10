package com.mufti.test.storeapps.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.mufti.test.storeapps.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(userList: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun getAllProduct(): List<ProductEntity>

    @Query("DELETE FROM products")
    suspend fun deleteAllProduct()

    @RawQuery(observedEntities = [ProductEntity::class])
    fun getFilterProduct(query: SupportSQLiteQuery): List<ProductEntity>
}
