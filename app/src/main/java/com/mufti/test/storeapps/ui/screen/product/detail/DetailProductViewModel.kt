package com.mufti.test.storeapps.ui.screen.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mufti.test.storeapps.data.StoreRepository
import com.mufti.test.storeapps.domain.model.Product

class DetailProductViewModel(private val repository: StoreRepository) : ViewModel() {

    fun detailProduct(id: Int): LiveData<Product> = repository.getDetailProduct(id)
}
