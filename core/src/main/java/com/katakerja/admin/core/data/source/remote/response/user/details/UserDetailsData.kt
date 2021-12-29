package com.katakerja.admin.core.data.source.remote.response.user.details

import com.google.gson.annotations.SerializedName

data class UserDetailsData(

	@field:SerializedName("tglLahir")
	val tglLahir: String,

	@field:SerializedName("telp")
	val telp: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("staff_sejak")
	val staffSejak: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("foto")
	val avatar: String?,

	@field:SerializedName("id_role")
	val idRole: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("member_sejak")
	val memberSejak: String
)