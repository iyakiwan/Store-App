package com.mufti.test.storeapps.ui.screen.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufti.test.storeapps.data.Result
import com.mufti.test.storeapps.data.StoreRepository
import com.mufti.test.storeapps.domain.model.Product
import kotlinx.coroutines.launch

class DetailProductViewModel(private val repository: StoreRepository) : ViewModel() {

    private val _product = MutableLiveData<Result<Product>>()
    val product: LiveData<Result<Product>> = _product

    fun getDetailProduct(id: Int) {
        _product.value = Result.Loading
        viewModelScope.launch {
            _product.value = repository.getDetailProduct(id)
        }
    }
}
