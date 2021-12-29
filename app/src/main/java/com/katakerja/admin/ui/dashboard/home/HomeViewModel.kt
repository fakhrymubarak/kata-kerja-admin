package com.katakerja.admin.ui.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.BorrowedBook
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.core.domain.usecase.book.BookUseCase
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val bookUseCase: BookUseCase,
) : ViewModel() {
    val user = MutableLiveData<User>()

    fun getUserById(authToken: String, idUser: Int): LiveData<Resource<User>> =
        userUseCase.getUserById(authToken, idUser).asLiveData()

    fun getBorrowedBooksById(idUser: Int): LiveData<Resource<List<BorrowedBook>>> =
        bookUseCase.getBorrowedBooksById(idUser).asLiveData()

    fun getAuthToken(): LiveData<String> = userUseCase.getAuthToken().asLiveData()
    fun getUserId(): LiveData<Int> = userUseCase.getUserId().asLiveData()
}