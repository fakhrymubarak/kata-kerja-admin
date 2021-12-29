package com.katakerja.admin.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    private lateinit var _authToken: String
    private var _userId by Delegates.notNull<Int>()

    init {
        viewModelScope.launch {
            userUseCase.getUserId().onEach { userId ->
                _userId = userId
            }.launchIn(this)

            userUseCase.getAuthToken().onEach { authToken ->
                _authToken = authToken
            }.launchIn(this)
        }
    }

    fun updateProfile(
        email: String, name: String, bornDate: String, phoneNumber: String,
    ): LiveData<Resource<User>> =
        userUseCase.updateUser(_authToken, _userId, email, name, bornDate, phoneNumber).asLiveData()

    fun logout() {
        userUseCase.clearUserDataStore()
    }
}