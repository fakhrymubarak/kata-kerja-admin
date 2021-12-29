package com.katakerja.admin.core.data.source.remote.response.book.details

import com.google.gson.annotations.SerializedName

data class BookDetailsData(

	@field:SerializedName("foto_buku")
	val fotoBuku: String?,

	@field:SerializedName("penerbit")
	val penerbit: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("isbn")
	val isbn: String,

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