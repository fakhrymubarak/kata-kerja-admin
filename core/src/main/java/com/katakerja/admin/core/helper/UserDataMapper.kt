package com.katakerja.admin.core.helper

import com.katakerja.admin.core.data.source.remote.response.user.all.UserData
import com.katakerja.admin.core.data.source.remote.response.user.details.UserDetailsData
import com.katakerja.admin.core.data.source.remote.response.user.login.LoginData
import com.katakerja.admin.core.data.source.remote.response.user.register.RegisterData
import com.katakerja.admin.core.data.source.remote.response.user.update.UserUpdateData
import com.katakerja.admin.core.domain.model.Login as LoginDomain
import com.katakerja.admin.core.domain.model.Register as RegisterDomain
import com.katakerja.admin.core.domain.model.User as UserDomain


object UserDataMapper {
    object User {
        fun mapResponseToDomain(userDetailData: UserDetailsData): UserDomain =
            UserDomain(
                idUser = userDetailData.id,
                email = userDetailData.email,
                idRole = userDetailData.idRole,
                name = userDetailData.name,
                avatar = userDetailData.avatar ?: "",
                fullAddress = "",
                bornDate = userDetailData.tglLahir.substring(0, 10),
                phoneNumber = userDetailData.telp,
                memberSince = userDetailData.memberSejak,
                staffSince = userDetailData.staffSejak,
            )

        fun mapResponseToDomain(userDetailData: UserUpdateData): UserDomain =
            UserDomain(
                idUser = userDetailData.id,
                email = userDetailData.email,
                idRole = userDetailData.idRole.toInt(),
                name = userDetailData.name,
                avatar = userDetailData.avatar ?: "",
                fullAddress = "",
                bornDate = userDetailData.tglLahir.substring(0, 10),
                phoneNumber = userDetailData.telp,
                memberSince = userDetailData.memberSejak,
                staffSince = userDetailData.staffSejak,
            )

        fun mapResponseToDomain(userData: UserData): UserDomain  =
            UserDomain(
                idUser = userData.id,
                email = userData.email,
                idRole = userData.idRole.toInt(),
                name = userData.name,
                avatar = userData.foto ?: "",
                fullAddress = "",
                bornDate = userData.tglLahir.substring(0, 10),
                phoneNumber = userData.telp,
                memberSince = userData.memberSejak,
                staffSince = userData.staffSejak,
            )

    }

    object Login {
        fun mapResponseToDomain(loginData: LoginData): LoginDomain =
            LoginDomain(
                token = loginData.token,
                id = loginData.id,
                name = loginData.name,
                idRole = loginData.idRole,
            )
    }

    object Register {
        fun mapResponseToDomain(registerData: RegisterData): RegisterDomain =
            RegisterDomain(
                token = registerData.token,
                id = registerData.id,
                name = registerData.name,
                idRole = registerData.idRole,
            )
    }
}