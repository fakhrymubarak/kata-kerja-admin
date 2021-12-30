package com.katakerja.admin.core.data.source.remote

import com.katakerja.admin.core.data.source.remote.network.ApiResponse
import com.katakerja.admin.core.data.source.remote.network.UserApiService
import com.katakerja.admin.core.data.source.remote.response.user.all.UserData
import com.katakerja.admin.core.data.source.remote.response.user.details.UserDetailsData
import com.katakerja.admin.core.data.source.remote.response.user.login.LoginData
import com.katakerja.admin.core.data.source.remote.response.user.register.RegisterData
import com.katakerja.admin.core.data.source.remote.response.user.update.UserUpdateData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteUserDataSource @Inject constructor(private val userApiService: UserApiService) {
    fun getAllUser(authToken: String): Flow<ApiResponse<List<UserData>>> =
        flow {
            try {
                val response = userApiService.getAllUser(authToken)
                if (response.success) {
                    emit(ApiResponse.Success(response.pagingData.userData))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e(e)
            }
        }.flowOn(Dispatchers.IO)

    fun getUserById(authToken: String, userId: Int): Flow<ApiResponse<UserDetailsData>> =
        flow {
            try {
                val response = userApiService.getUserById(authToken, userId)
                if (response.success) {
                    emit(ApiResponse.Success(response.userDetailsData))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e(e)
            }
        }.flowOn(Dispatchers.IO)

    fun updateUserById(authToken: String, userId: Int): Flow<ApiResponse<UserUpdateData>> =
        flow {
            try {
                val response = userApiService.updateUserById(authToken, userId)
                if (response.success) {
                    emit(ApiResponse.Success(response.userUpdateData))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e(e)
            }
        }.flowOn(Dispatchers.IO)

    fun postLogin(email: String, password: String): Flow<ApiResponse<LoginData>> =
        flow {
            try {
                val response = userApiService.postLogin(email, password)
                if (response.success) {
                    emit(ApiResponse.Success(response.loginData))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e(e)
            }
        }.flowOn(Dispatchers.IO)

    fun postRegister(
        email: String, password: String, name: String, bornDate: String, phoneNumber: String,
    ): Flow<ApiResponse<RegisterData>> =
        flow {
            try {
                val response = userApiService.postRegister(
                    email, password, name, bornDate, phoneNumber, currentDate
                )
                if (response.success) {
                    emit(ApiResponse.Success(response.registerData))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e(e)
            }
        }.flowOn(Dispatchers.IO)

    fun updateProfile(
        authToken: String,
        userId: Int,
        email: String,
        name: String,
        bornDate: String,
        phoneNumber: String
    ): Flow<ApiResponse<UserUpdateData>> =
        flow {
            try {
                val response = userApiService.updateProfile(
                    authToken,
                    userId,
                    email,
                    name,
                    bornDate,
                    phoneNumber
                )
                if (response.success) {
                    emit(ApiResponse.Success(response.userUpdateData))
                } else {
                    emit(ApiResponse.Error(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Timber.e(e)
            }
        }.flowOn(Dispatchers.IO)

    private val currentDate =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
}