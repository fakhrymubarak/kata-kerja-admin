package com.katakerja.admin.core.data.source.remote.response.book.search

import com.google.gson.annotations.SerializedName

data class SearchedBookData(

	@field:SerializedName("foto_buku")
	val fotoBuku: String?,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("penerbit")
	val penerbit: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("isbn")
	val isbn: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("kategori")
	val kategori: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("judul")
	val judul: String,

	@field:SerializedName("tahun_terbit")
	val tahunTerbit: Int,

	@field:SerializedName("stock")
	val stock: Int
)