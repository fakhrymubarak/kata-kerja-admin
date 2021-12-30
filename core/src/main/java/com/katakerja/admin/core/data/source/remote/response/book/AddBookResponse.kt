package com.katakerja.admin.core.data.source.remote.response.book

import com.google.gson.annotations.SerializedName

data class AddBookResponse(

    @field:SerializedName("data")
	val bookData: BookData,

    @field:SerializedName("success")
	val success: Boolean,

    @field:SerializedName("message")
	val message: String
)