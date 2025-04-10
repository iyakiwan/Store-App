package com.mufti.test.storeapps.ui.screen.auth.first

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mufti.test.storeapps.data.StoreRepository

class FirstViewModel(private val repository: StoreRepository) : ViewModel() {

    fun getLoginUser() = repository.getLoginUser().asLiveData()
}
