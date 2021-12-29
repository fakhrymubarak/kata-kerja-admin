package com.katakerja.admin.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun postLogin(email: String, password: String) =
        userUseCase.postLogin(email, password).asLiveData()

    fun saveAuthToken(authToken: String) {
        userUseCase.saveAuthToken(authToken)
    }

    fun saveUserId(userId: Int) {
        userUseCase.saveUserId(userId)
    }
}