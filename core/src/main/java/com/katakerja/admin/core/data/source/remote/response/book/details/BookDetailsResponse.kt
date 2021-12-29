package com.katakerja.admin.core.data.source.remote.response.book.details

import com.google.gson.annotations.SerializedName

data class BookDetailsResponse(

	@field:SerializedName("data")
	val bookDetailsData: BookDetailsData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)