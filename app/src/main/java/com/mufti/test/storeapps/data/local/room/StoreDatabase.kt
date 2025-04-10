package com.mufti.test.storeapps.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mufti.test.storeapps.data.local.entity.CartEntity
import com.mufti.test.storeapps.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class, CartEntity::class], version = 1, exportSchema = false)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun productDao(): StoreDao

    companion object {
        @Volatile
        private var instance: StoreDatabase? = null
        fun getInstance(context: Context): StoreDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoreDatabase::class.java, "store.db"
                ).allowMainThreadQueries().build()
            }
    }
}
