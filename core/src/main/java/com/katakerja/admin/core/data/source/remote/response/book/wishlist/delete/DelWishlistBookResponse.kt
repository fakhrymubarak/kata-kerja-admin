package com.katakerja.admin.core.data.source.remote.response.book.wishlist.delete

import com.google.gson.annotations.SerializedName

data class DelWishlistBookResponse(

	@field:SerializedName("data")
	val data: List<Nothing>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)