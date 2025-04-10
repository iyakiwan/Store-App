package com.mufti.test.storeapps.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mufti.test.storeapps.data.StoreRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: StoreRepository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            repository.setLoginUser(isLogin = false)
        }
    }

    fun getUserName() = repository.getLoginUser().asLiveData()
}
