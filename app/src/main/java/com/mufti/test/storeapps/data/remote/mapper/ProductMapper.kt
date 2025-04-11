package com.mufti.test.storeapps.data.remote.mapper

import com.mufti.test.storeapps.data.local.entity.CartAndProduct
import com.mufti.test.storeapps.data.local.entity.ProductEntity
import com.mufti.test.storeapps.data.remote.request.product.ProductResponse
import com.mufti.test.storeapps.domain.model.Cart
import com.mufti.test.storeapps.domain.model.Product

object ProductMapper {

    fun mapListProductResponseToListProductEntity(listProductResponse: List<ProductResponse>): List<ProductEntity> {
        val products = ArrayList<ProductEntity>()

        listProductResponse.map {
            products.add(
                ProductEntity(
                    id = it.id ?: 0,
                    title = it.title.orEmpty(),
                    description = it.description.orEmpty(),
                    price = it.price ?: 0.0,
                    image = it.image.orEmpty(),
                    category = it.category.orEmpty(),
                    ratingRate = it.rating?.rate?.toString() ?: "0.0",
                    ratingCount = it.rating?.count ?: 0,
                )
            )
        }
        return products
    }

    fun mapListProductEntityToListProduct(listProductEntity: List<ProductEntity>): List<Product> {
        return listProductEntity.map { it.toProduct() }
    }

    fun CartAndProduct.toCart(): Cart {
        return Cart(
            id = this.cartEntity.id,
            quantity = this.cartEntity.quantity,
            product = this.productEntity.toProduct()
        )
    }

    fun ProductEntity.toProduct(): Product {
        return Product(
            image = this.image,
            price = this.price,
            description = this.description,
            id = this.id,
            title = this.title,
            category = this.category,
            ratingRate = this.ratingRate,
            ratingCount = this.ratingCount
        )
    }
}
