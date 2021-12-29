package com.katakerja.admin.core.data.source.remote.response.user.details

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(

	@field:SerializedName("data")
	val userDetailsData: UserDetailsData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)