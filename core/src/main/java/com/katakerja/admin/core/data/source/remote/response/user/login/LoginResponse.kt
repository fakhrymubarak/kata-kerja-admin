package com.katakerja.admin.core.data.source.remote.response.user.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val loginData: LoginData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)