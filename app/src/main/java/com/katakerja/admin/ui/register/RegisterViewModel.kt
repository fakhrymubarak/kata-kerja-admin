package com.katakerja.admin.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Register
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun postRegister(
        email: String,
        password: String,
        name: String,
        bornDate: String,
        phoneNumber: String,
    ): LiveData<Resource<Register>> =
        userUseCase.postRegister(email, password, name, bornDate, phoneNumber).asLiveData()
}