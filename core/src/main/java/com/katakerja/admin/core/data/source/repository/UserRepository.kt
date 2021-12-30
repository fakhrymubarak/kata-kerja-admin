package com.katakerja.admin.core.data.source.repository

import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.data.source.local.LocalUserDataSource
import com.katakerja.admin.core.data.source.remote.RemoteUserDataSource
import com.katakerja.admin.core.data.source.remote.network.ApiResponse
import com.katakerja.admin.core.data.source.remote.response.user.all.UserData
import com.katakerja.admin.core.domain.model.Login
import com.katakerja.admin.core.domain.model.Register
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.core.domain.repository.IUserRepository
import com.katakerja.admin.core.helper.UserDataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : IUserRepository {
    override fun getAllUser(authToken: String): Flow<Resource<List<User>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteUserDataSource.getAllUser(authToken).first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data.map{UserDataMapper.User.mapResponseToDomain(it)}
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
                is ApiResponse.Empty -> {}

            }
        }

    override fun getUserById(authToken: String, userId: Int): Flow<Resource<User>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteUserDataSource.getUserById(authToken, userId).first()) {
                is ApiResponse.Success -> {
                    val data = UserDataMapper.User.mapResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
                is ApiResponse.Empty -> {}

            }
        }

    override fun updateUserById(authToken: String, userId: Int): Flow<Resource<User>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse =
                remoteUserDataSource.updateUserById(authToken, userId).first()) {
                is ApiResponse.Success -> {
                    val data = UserDataMapper.User.mapResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
                is ApiResponse.Empty -> {}

            }
        }

    override fun postLogin(email: String, password: String): Flow<Resource<Login>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse =
                remoteUserDataSource.postLogin(email, password).first()) {
                is ApiResponse.Success -> {
                    val data = UserDataMapper.Login.mapResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
                is ApiResponse.Empty -> {}

            }
        }

    override fun postRegister(
        email: String,
        password: String,
        name: String,
        bornDate: String,
        phoneNumber: String
    ): Flow<Resource<Register>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse =
                remoteUserDataSource.postRegister(
                    email,
                    password,
                    name,
                    bornDate,
                    phoneNumber
                )
                    .first()) {
                is ApiResponse.Success -> {
                    val data = UserDataMapper.Register.mapResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
                is ApiResponse.Empty -> {}

            }
        }

    override fun updateUser(
        authToken: String,
        userId: Int,
        email: String,
        name: String,
        bornDate: String,
        phoneNumber: String
    ): Flow<Resource<User>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse =
                remoteUserDataSource.updateProfile(
                    authToken,
                    userId,
                    email,
                    name,
                    bornDate,
                    phoneNumber
                ).first()) {
                is ApiResponse.Success -> {
                    val data = UserDataMapper.User.mapResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
                is ApiResponse.Empty -> {}

            }
        }

    override fun saveAuthToken(authToken: String) {
        CoroutineScope(Dispatchers.IO).launch { localUserDataSource.saveAuthToken(authToken) }
    }

    override fun getAuthToken(): Flow<String> =
        flow {
            emitAll(localUserDataSource.getAuthToken())
        }

    override fun getUserId(): Flow<Int> =
        flow {
            emitAll(localUserDataSource.getUserId())
        }

    override fun saveUserId(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch { localUserDataSource.saveUserId(userId) }
    }

    override fun clearUserDataStore() {
        CoroutineScope(Dispatchers.IO).launch { localUserDataSource.clearUserDataStore() }
    }
}