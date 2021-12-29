package com.katakerja.admin.core.data.source.remote.response.user.register

import com.google.gson.annotations.SerializedName

data class RegisterData(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id_role")
    val idRole: Int,

    @field:SerializedName("token")
    val token: String
)