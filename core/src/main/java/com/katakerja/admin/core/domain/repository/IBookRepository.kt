package com.katakerja.admin.core.domain.repository

import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Book
import com.katakerja.admin.core.domain.model.BorrowedBook
import com.katakerja.admin.core.domain.model.WishedBook
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    fun addBook(
        authToken: String, isbn: String, title: String, author: String, imgCover: String,
        releaseYear: Int, publisher: String, category: String, stock: Int, description: String
    ): Flow<Resource<Nothing>>

    fun getBorrowedBooksById(idUser: Int): Flow<Resource<List<BorrowedBook>>>
    fun getBookDetailsById(bookId: Int): Flow<Resource<Book>>
    fun getSearchedBooks(query: String): Flow<Resource<List<Book>>>
    fun getBooksByCat(category: String): Flow<Resource<List<Book>>>
    fun getWishBooks(idUser: Int): Flow<Resource<List<WishedBook>>>
    fun insertWishBooks(authToken: String, idUser: Int, idBook: Int): Flow<Resource<Nothing>>
    fun delWishBook(authToken: String, idWish: Int): Flow<Resource<List<Nothing>>>
}