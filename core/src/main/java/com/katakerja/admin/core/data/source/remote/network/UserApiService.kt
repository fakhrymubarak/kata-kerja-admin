package com.katakerja.admin.core.data.source.remote.network

import com.katakerja.admin.core.data.source.remote.response.user.details.UserDetailsResponse
import com.katakerja.admin.core.data.source.remote.response.user.login.LoginResponse
import com.katakerja.admin.core.data.source.remote.response.user.register.RegisterResponse
import com.katakerja.admin.core.data.source.remote.response.user.update.UserUpdateResponse
import retrofit2.http.*

interface UserApiService {
    /* Details User */
    @GET("users/show/{id}")
    suspend fun getUserById(
        @Header("Authorization") authToken: String,
        @Path("id") idUser: Int,
    ): UserDetailsResponse

    /* Update User */
    @PUT("users/update/{id}")
    suspend fun updateUserById(
        @Header("Authorization") authToken: String,
        @Path("id") idUser: Int,
    ): UserUpdateResponse

    /* Login */
    @FormUrlEncoded
    @POST("users/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    /* Register */
    @FormUrlEncoded
    @POST("users/register")
    suspend fun postRegister(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("tglLahir") tglLahir: String,
        @Field("telp") telp: String,
        @Field("member_sejak") memberSejak: String,
        @Field("c_password") c_password: String = password,
        @Field("staff_sejak") staffSejak: String = "2000-01-01",
        @Field("id_role") idRole: Int = 4,
    ): RegisterResponse

    /* Update Profile */
    @FormUrlEncoded
    @PUT("users/update/{id}")
    suspend fun updateProfile(
        @Header("Authorization") authToken: String,
        @Path("id") idUser: Int,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("tglLahir") tglLahir: String,
        @Field("telp") telp: String,
    ): UserUpdateResponse
}