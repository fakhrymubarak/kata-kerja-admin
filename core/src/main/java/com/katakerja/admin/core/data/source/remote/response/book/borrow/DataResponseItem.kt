package com.katakerja.admin.core.data.source.remote.response.book.borrow

import com.google.gson.annotations.SerializedName

data class DataResponseItem(

	@field:SerializedName("books")
	val borrowedBooksData: BorrowedBooksData,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("borrow_date")
	val borrowDate: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("book_id")
	val bookId: Int,

	@field:SerializedName("users")
	val users: Users,

	@field:SerializedName("return_date")
	val returnDate: String,

	@field:SerializedName("status")
	val status: Int
)