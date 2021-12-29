package com.katakerja.admin.core.data.source.remote.response.book.search

import com.google.gson.annotations.SerializedName

data class PagingData(

	@field:SerializedName("per_page")
	val perPage: Int,

	@field:SerializedName("data")
	val listSearchedBookData: List<SearchedBookData>,

	@field:SerializedName("last_page")
	val lastPage: Int,

	@field:SerializedName("next_page_url")
	val nextPageUrl: Any,

	@field:SerializedName("prev_page_url")
	val prevPageUrl: Any,

	@field:SerializedName("first_page_url")
	val firstPageUrl: String,

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("last_page_url")
	val lastPageUrl: String,

	@field:SerializedName("from")
	val from: Int,

	@field:SerializedName("links")
	val links: List<LinksItem>,

	@field:SerializedName("to")
	val to: Int,

	@field:SerializedName("current_page")
	val currentPage: Int
)