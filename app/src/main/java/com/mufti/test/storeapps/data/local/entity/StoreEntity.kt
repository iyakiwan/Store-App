package com.mufti.test.storeapps.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "rating_rate")
    val ratingRate: String,
    @ColumnInfo(name = "rating_count")
    val ratingCount: Int
)

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "product_id")
    val productId: Int,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
)

data class CartAndProduct(
    @Embedded
    val cartEntity: CartEntity,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val productEntity: ProductEntity
)
