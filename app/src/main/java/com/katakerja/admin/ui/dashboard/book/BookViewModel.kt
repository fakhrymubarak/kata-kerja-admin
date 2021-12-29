package com.katakerja.admin.ui.dashboard.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.core.domain.model.BorrowedBook
import com.katakerja.admin.core.domain.usecase.book.BookUseCase
import com.katakerja.admin.helper.DataDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val bookUseCase: BookUseCase) : ViewModel() {
    fun getDummyBorrowedBooks(): LiveData<List<BorrowedBook>> = DataDummy.getBorrowedBooks()

    fun searchBooks(query: String): LiveData<Resource<List<Book>>> =
        bookUseCase.getSearchedBooks(query).asLiveData()

    fun getBooksByCat(category: String): LiveData<Resource<List<Book>>> =
        bookUseCase.getBooksByCat(category).asLiveData()

    fun getDetailBooks(idBook: Int): LiveData<Resource<Book>> =
        bookUseCase.getBookDetailsById(idBook).asLiveData()
}