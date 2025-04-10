package com.mufti.test.storeapps.domain.model

data class Product(
    val image: String,
    val price: Double,
    val description: String,
    val id: Int,
    val title: String,
    val category: String,
    val ratingRate: String,
    val ratingCount: Int
)
