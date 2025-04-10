package com.mufti.test.storeapps.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufti.test.storeapps.data.Result
import com.mufti.test.storeapps.data.StoreRepository
import com.mufti.test.storeapps.domain.model.Product
import com.mufti.test.storeapps.utils.constant.CategoryProductType
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: StoreRepository) : ViewModel() {

    private val _listProduct = MutableLiveData<Result<List<Product>>>()
    val listProduct: LiveData<Result<List<Product>>> = _listProduct

    private val _categoryType = MutableLiveData(CategoryProductType.ALL)
    val categoryType: LiveData<CategoryProductType> = _categoryType

    fun getListProduct() {
        _listProduct.value = Result.Loading
        viewModelScope.launch {
            _listProduct.value = repository.getListProduct()
        }
    }

    fun setCategoryProduct(categoryType: CategoryProductType) {
        _listProduct.value = Result.Loading
        _categoryType.value = categoryType

        _listProduct.value = repository.getFilterProduct(categoryType)
    }
}
