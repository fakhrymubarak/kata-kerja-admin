package com.katakerja.admin.core.data.source.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("user")

@Singleton
class UserDataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val userDataStore = appContext.dataStore

    fun getAuthToken(): Flow<String> =
        userDataStore.data.map { preferences ->
            preferences[AUTH_TOKEN] ?: ""
        }

    suspend fun saveAuthToken(authToken: String) {
        userDataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = authToken
        }
    }

    fun getUserId(): Flow<Int> =
        userDataStore.data.map { preferences ->
            preferences[USER_ID] ?: 0
        }

    suspend fun saveUserId(userId: Int) {
        userDataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    suspend fun clearUserDataStore() =
        userDataStore.edit { allPreference ->
            allPreference.clear()
        }

    companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val USER_ID = intPreferencesKey("user_id")
    }
}