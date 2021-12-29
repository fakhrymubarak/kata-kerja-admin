package com.katakerja.admin.core.data.source.remote.response.user.update

import com.google.gson.annotations.SerializedName

data class UserUpdateResponse(

    @field:SerializedName("data")
	val userUpdateData: UserUpdateData,

    @field:SerializedName("success")
	val success: Boolean,

    @field:SerializedName("message")
	val message: String
)