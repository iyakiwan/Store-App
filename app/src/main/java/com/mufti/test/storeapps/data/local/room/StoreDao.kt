package com.mufti.test.storeapps.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.mufti.test.storeapps.data.local.entity.CartAndProduct
import com.mufti.test.storeapps.data.local.entity.CartEntity
import com.mufti.test.storeapps.data.local.entity.ProductEntity

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(userList: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun getAllProduct(): List<ProductEntity>

    @Query("DELETE FROM products")
    suspend fun deleteAllProduct()

    @RawQuery(observedEntities = [ProductEntity::class])
    fun getFilterProduct(query: SupportSQLiteQuery): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity)

    @Transaction
    @Query("SELECT * from carts")
    fun getAllCartAndProduct(): LiveData<List<CartAndProduct>>

    @Query("UPDATE carts SET quantity = :quantity WHERE id = :cartId")
    suspend fun updateCartQuantity(cartId: Int, quantity: Int)

    @Query("DELETE FROM carts WHERE id = :cartId")
    suspend fun deleteCart(cartId: Int)
}
