package com.katakerja.admin.core.data.source.local

import com.katakerja.admin.core.data.source.local.datastore.UserDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalUserDataSource @Inject constructor(private val userDataStoreManager: UserDataStoreManager) {
    fun getAuthToken(): Flow<String> = userDataStoreManager.getAuthToken()

    suspend fun saveAuthToken(authToken: String) {
        userDataStoreManager.saveAuthToken(authToken)
    }
    fun getUserId(): Flow<Int> = userDataStoreManager.getUserId()

    suspend fun saveUserId(userId: Int) {
        userDataStoreManager.saveUserId(userId)
    }

    suspend fun clearUserDataStore() {
        userDataStoreManager.clearUserDataStore()
    }
}