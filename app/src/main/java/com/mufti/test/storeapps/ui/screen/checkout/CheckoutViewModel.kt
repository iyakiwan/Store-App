package com.mufti.test.storeapps.ui.screen.checkout

import androidx.lifecycle.ViewModel
import com.mufti.test.storeapps.data.StoreRepository

class CheckoutViewModel(private val repository: StoreRepository) : ViewModel() {

    fun getAllCart() = repository.getAllCartAndProduct()
}
