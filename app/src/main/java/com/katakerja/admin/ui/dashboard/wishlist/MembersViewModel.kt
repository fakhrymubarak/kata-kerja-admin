package com.katakerja.admin.ui.dashboard.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MembersViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    fun getAuthToken(): LiveData<String> =
        userUseCase.getAuthToken().asLiveData()

    fun getAllMembers(authToken: String): LiveData<Resource<List<User>>> =
        userUseCase.getAllUser(authToken).asLiveData()
}