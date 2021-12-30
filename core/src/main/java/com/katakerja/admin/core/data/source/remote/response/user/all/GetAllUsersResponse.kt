package com.katakerja.admin.core.data.source.remote.response.user.all

import com.google.gson.annotations.SerializedName

data class GetAllUsersResponse(

    @field:SerializedName("data")
	val pagingData: PagingData,

    @field:SerializedName("success")
	val success: Boolean,

    @field:SerializedName("message")
	val message: String
)