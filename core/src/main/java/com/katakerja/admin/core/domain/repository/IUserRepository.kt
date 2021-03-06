package com.katakerja.admin.core.domain.repository

import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.data.source.remote.network.ApiResponse
import com.katakerja.admin.core.data.source.remote.response.user.all.UserData
import com.katakerja.admin.core.domain.model.Login
import com.katakerja.admin.core.domain.model.Register
import com.katakerja.admin.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getAllUser(authToken: String): Flow<Resource<List<User>>>
    fun getUserById(authToken: String, userId: Int): Flow<Resource<User>>
    fun updateUserById(authToken: String, userId: Int): Flow<Resource<User>>
    fun postLogin(email: String, password: String): Flow<Resource<Login>>
    fun postRegister(
        email: String,
        password: String,
        name: String,
        bornDate: String,
        phoneNumber: String,
    ): Flow<Resource<Register>>
    fun updateUser(
        authToken: String,
        userId: Int,
        email: String,
        name: String,
        bornDate: String,
        phoneNumber: String
    ): Flow<Resource<User>>
    fun saveAuthToken(authToken: String)
    fun getAuthToken(): Flow<String>

    fun saveUserId(userId: Int)
    fun getUserId(): Flow<Int>
    fun clearUserDataStore()
}