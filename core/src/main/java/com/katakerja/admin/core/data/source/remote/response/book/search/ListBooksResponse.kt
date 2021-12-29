package com.katakerja.admin.core.data.source.remote.response.book.search

import com.google.gson.annotations.SerializedName

data class ListBooksResponse(

    @field:SerializedName("data")
	val pagingData: PagingData,

    @field:SerializedName("success")
	val success: Boolean,

    @field:SerializedName("message")
	val message: String
)