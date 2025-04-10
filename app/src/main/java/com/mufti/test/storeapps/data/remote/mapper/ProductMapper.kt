package com.mufti.test.storeapps.data.remote.mapper

import com.mufti.test.storeapps.data.remote.request.product.ProductResponse
import com.mufti.test.storeapps.domain.model.Product

object ProductMapper {

    fun mapListUserEntityToListUser(listProductResponse: List<ProductResponse>): List<Product> {
        val users = ArrayList<Product>()

        listProductResponse.map {
            users.add(
                Product(
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
        return users
    }
}
