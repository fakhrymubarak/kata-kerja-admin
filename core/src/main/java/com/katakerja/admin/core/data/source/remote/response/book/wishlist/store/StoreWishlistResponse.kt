package com.katakerja.admin.core.data.source.remote.response.book.wishlist.store

import com.google.gson.annotations.SerializedName

data class StoreWishlistResponse(

	@field:SerializedName("data")
	val storeWishlistData: StoreWishlistData,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)