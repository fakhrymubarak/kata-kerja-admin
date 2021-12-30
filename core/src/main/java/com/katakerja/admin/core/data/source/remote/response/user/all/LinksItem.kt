package com.katakerja.admin.core.data.source.remote.response.user.all

import com.google.gson.annotations.SerializedName

data class LinksItem(

	@field:SerializedName("active")
	val active: Boolean,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("url")
	val url: Any
)