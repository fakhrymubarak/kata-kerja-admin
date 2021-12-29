package com.katakerja.admin.core.data.source.remote.response.book.borrow

import com.google.gson.annotations.SerializedName

data class BorrowedBooksResponse(

	@field:SerializedName("data")
	val dataResponse: DataResponse,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)