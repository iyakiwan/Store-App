package com.mufti.test.storeapps.ui.screen.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufti.test.storeapps.data.Result
import com.mufti.test.storeapps.data.StoreRepository
import com.mufti.test.storeapps.data.remote.request.auth.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StoreRepository) : ViewModel() {
    private val _login = MutableLiveData<Result<Boolean>>()
    val login: LiveData<Result<Boolean>> = _login

    private val _validationInput = MutableLiveData<LoginInputState>()
    val validationInput: LiveData<LoginInputState> = _validationInput

    sealed class LoginInputState {
        data object EmptyUsername : LoginInputState()
        data object EmptyPassword : LoginInputState()
        data object ValidInput : LoginInputState()
    }

    fun validationInput(username: String, password: String) {
        _validationInput.value = if (username.isEmpty()) {
            LoginInputState.EmptyUsername
        } else if (password.isEmpty()) {
            LoginInputState.EmptyPassword
        } else {
            LoginInputState.ValidInput
        }
    }

    fun login(username: String, password: String) {
        _login.value = Result.Loading
        viewModelScope.launch {
            val request = LoginRequest(
                username = username, password = password
            )
            _login.value = repository.login(request)
        }
    }
}
