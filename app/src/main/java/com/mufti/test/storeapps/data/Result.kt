package com.mufti.test.storeapps.data

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val code: Int? = null, val error: String) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}
