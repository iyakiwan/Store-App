package com.mufti.test.storeapps.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("token")
	val token: String? = null
)
