package com.katakerja.admin.core.data.source.remote.response.book.wishlist.store

import com.google.gson.annotations.SerializedName

data class StoreWishlistData(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("book_id")
	val bookId: String,

	@field:SerializedName("id")
	val id: Int
)