package com.katakerja.admin.ui.dashboard.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.WishedBook
import com.katakerja.admin.core.domain.usecase.book.BookUseCase
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val bookUseCase: BookUseCase,
) : ViewModel() {

    fun getUserId(): LiveData<Int> = userUseCase.getUserId().asLiveData()

    fun getWishlistBooks(idUser: Int): LiveData<Resource<List<WishedBook>>> =
        bookUseCase.getWishBooks(idUser).asLiveData()
}