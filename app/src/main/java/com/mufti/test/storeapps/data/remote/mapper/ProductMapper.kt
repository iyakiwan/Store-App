package com.mufti.test.storeapps.data.remote.mapper

import com.mufti.test.storeapps.data.local.entity.ProductEntity
import com.mufti.test.storeapps.data.remote.request.product.ProductResponse
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
        return listProductEntity.map {
            Product(
                id = it.id,
                title = it.title,
                description = it.description,
                price = it.price,
                image = it.image,
                category = it.category,
                ratingRate = it.ratingRate,
                ratingCount = it.ratingCount,
            )
        }
    }

    fun mapProductResponseToProduct(productResponse: ProductResponse): Product {
        return Product(
            id = productResponse.id ?: 0,
            title = productResponse.title.orEmpty(),
            description = productResponse.description.orEmpty(),
            price = productResponse.price ?: 0.0,
            image = productResponse.image.orEmpty(),
            category = productResponse.category.orEmpty(),
            ratingRate = productResponse.rating?.rate?.toString() ?: "0.0",
            ratingCount = productResponse.rating?.count ?: 0,
        )

    }
}
