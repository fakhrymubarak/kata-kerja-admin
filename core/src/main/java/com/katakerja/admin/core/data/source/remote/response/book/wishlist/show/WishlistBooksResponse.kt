package com.katakerja.admin.core.data.source.remote.response.book.wishlist.show

import com.google.gson.annotations.SerializedName

data class WishlistBooksResponse(

	@field:SerializedName("data")
	val listWishlistBooksData: List<ListWishlistBooksData>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)