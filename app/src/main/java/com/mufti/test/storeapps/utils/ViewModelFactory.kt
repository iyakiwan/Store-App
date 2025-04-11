package com.mufti.test.storeapps.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mufti.test.storeapps.data.StoreRepository
import com.mufti.test.storeapps.di.Injection
import com.mufti.test.storeapps.ui.screen.auth.first.FirstViewModel
import com.mufti.test.storeapps.ui.screen.auth.login.LoginViewModel
import com.mufti.test.storeapps.ui.screen.cart.CartViewModel
import com.mufti.test.storeapps.ui.screen.checkout.CheckoutViewModel
import com.mufti.test.storeapps.ui.screen.home.HomeViewModel
import com.mufti.test.storeapps.ui.screen.product.add.AddCartViewModel
import com.mufti.test.storeapps.ui.screen.product.detail.DetailProductViewModel
import com.mufti.test.storeapps.ui.screen.profile.ProfileViewModel

class ViewModelFactory private constructor(
    private val repository: StoreRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FirstViewModel::class.java)) {
            return FirstViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
            return DetailProductViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddCartViewModel::class.java)) {
            return AddCartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}
