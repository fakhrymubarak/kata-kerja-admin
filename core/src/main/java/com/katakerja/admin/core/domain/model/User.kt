package com.katakerja.admin.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val idUser: Int,
    val email: String,
    val idRole: Int,
    val name: String,
    val avatar: String,
    val fullAddress: String,
    val bornDate: String,
    val phoneNumber: String,
    val memberSince: String,
    val staffSince: String,
): Parcelable
