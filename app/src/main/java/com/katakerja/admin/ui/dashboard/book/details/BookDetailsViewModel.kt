package com.katakerja.admin.ui.dashboard.book.details

import androidx.lifecycle.*
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.core.domain.usecase.book.BookUseCase
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val bookUseCase: BookUseCase
) : ViewModel() {
    private var _userId by Delegates.notNull<Int>()
    private var _wishId by Delegates.notNull<Int>()
    private lateinit var _authToken: String

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

    fun getWishListStatus(idBook: Int): LiveData<Boolean> {
        val watchlistStatus = MutableLiveData<Boolean>()
        viewModelScope.launch {
            bookUseCase.getWishBooks(_userId).onEach { listResourceWish ->
                if (listResourceWish is Resource.Success) {
                    listResourceWish.data?.forEach { wishedBook ->
                        _wishId = wishedBook.id
                        if (wishedBook.bookData.idBook == idBook) {
                            watchlistStatus.postValue(true)
                        } else {
                            watchlistStatus.postValue(false)
                        }
                    }
                }
            }.launchIn(this)
        }

        return watchlistStatus
    }

    fun getDetailBooks(idBook: Int): LiveData<Resource<Book>> =
        bookUseCase.getBookDetailsById(idBook).asLiveData()

    fun insertWishBook(idBook: Int): LiveData<Resource<Nothing>> =
        bookUseCase.insertWishBooks(_authToken, _userId, idBook).asLiveData()

    fun delWishBook(): LiveData<Resource<List<Nothing>>> =
        bookUseCase.delWishBook(_authToken, _wishId).asLiveData()
}

