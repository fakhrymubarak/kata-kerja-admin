package com.katakerja.admin.core.domain.usecase.user

import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Login
import com.katakerja.admin.core.domain.model.Register
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val mIUserRepository: IUserRepository) :
    UserUseCase {
    override fun getUserById(authToken: String, userId: Int): Flow<Resource<User>> =
        mIUserRepository.getUserById(authToken, userId)

    override fun updateUserById(authToken: String, userId: Int): Flow<Resource<User>> =
        mIUserRepository.updateUserById(authToken, userId)

    override fun postLogin(email: String, password: String): Flow<Resource<Login>> =
        mIUserRepository.postLogin(email, password)

    override fun postRegister(
        email: String,
        password: String,
        name: String,
        bornDate: String,
        phoneNumber: String
    ): Flow<Resource<Register>> =
        mIUserRepository.postRegister(email, password, name, bornDate, phoneNumber)

    override fun updateUser(
        authToken: String,
        userId: Int,
        email: String,
        name: String,
        bornDate: String,
        phoneNumber: String
    ): Flow<Resource<User>> =
        mIUserRepository.updateUser(authToken, userId, email, name, bornDate, phoneNumber)

    override fun saveAuthToken(authToken: String) {
        mIUserRepository.saveAuthToken(authToken)
    }

    override fun getAuthToken(): Flow<String> = mIUserRepository.getAuthToken()

    override fun saveUserId(userId: Int) {
        mIUserRepository.saveUserId(userId)
    }

    override fun getUserId(): Flow<Int> = mIUserRepository.getUserId()

    override fun clearUserDataStore() {
        mIUserRepository.clearUserDataStore()
    }

}