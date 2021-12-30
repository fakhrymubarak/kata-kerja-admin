package com.katakerja.admin.ui.dashboard.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.core.domain.usecase.book.BookUseCase
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val bookUseCase: BookUseCase
) : ViewModel() {
    private lateinit var _authToken: String
    init {
        viewModelScope.launch {
        userUseCase.getAuthToken().onEach { authToken ->
                _authToken = authToken
            }.launchIn(this)
        }
    }

    fun addBook(
        isbn: String, title: String, author: String, imgCover: String,
        releaseYear: Int, publisher: String, category: String, stock: Int, description: String
    ): LiveData<Resource<Nothing>> = bookUseCase.addBook(
        authToken = _authToken,
        isbn = isbn,
        title = title,
        author = author,
        imgCover = imgCover,
        releaseYear = releaseYear,
        publisher = publisher,
        category = category,
        stock = stock,
        description = description,
    ).asLiveData()

    fun searchBooks(query: String): LiveData<Resource<List<Book>>> =
        bookUseCase.getSearchedBooks(query).asLiveData()

    fun getBooksByCat(category: String): LiveData<Resource<List<Book>>> =
        bookUseCase.getBooksByCat(category).asLiveData()

    fun getDetailBooks(idBook: Int): LiveData<Resource<Book>> =
        bookUseCase.getBookDetailsById(idBook).asLiveData()
}