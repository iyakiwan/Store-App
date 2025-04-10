package com.mufti.test.storeapps.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufti.test.storeapps.data.StoreRepository
import kotlinx.coroutines.launch

class CartViewModel(private val repository: StoreRepository) : ViewModel() {

    fun getAllCart() = repository.getAllCartAndProduct()

    fun updateCartQuantity(cartId: Int, quantity: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(cartId, quantity)
        }
    }

    fun deleteCart(cartId: Int) {
        viewModelScope.launch {
            repository.deleteCart(cartId)
        }
    }
}
