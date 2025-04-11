package com.mufti.test.storeapps.ui.screen.product.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufti.test.storeapps.data.StoreRepository
import com.mufti.test.storeapps.data.local.entity.CartEntity
import com.mufti.test.storeapps.domain.model.Product
import kotlinx.coroutines.launch

class AddCartViewModel(private val repository: StoreRepository) : ViewModel() {

    private var _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity

    var updateCart: Boolean = false

    fun setQuantity(value: Int) {
        _quantity.value = _quantity.value?.plus(value)
    }

    fun detailProduct(id: Int): LiveData<Product> = repository.getDetailProduct(id)

    fun setCart(productId: Int) {
        viewModelScope.launch {
            val quantity = repository.getCartQuantity(productId)
            updateCart = quantity > 0
            _quantity.value = if (quantity == 0) 1 else quantity
        }
    }

    fun submitCart(productId: Int) {
        if (updateCart) {
            updateCartQuantity(productId, _quantity.value ?: 1)
        } else {
            addToCart(productId)
        }
    }

    private fun addToCart(productId: Int) {
        viewModelScope.launch {
            val cart = CartEntity(productId = productId, quantity = _quantity.value ?: 1)
            repository.insertCart(cart)
        }
    }

    private fun updateCartQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            repository.updateCartQuantityByProductId(productId, quantity)
        }
    }
}
