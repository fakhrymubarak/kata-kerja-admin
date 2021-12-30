package com.katakerja.admin.core.data.source.remote.network

import com.katakerja.admin.core.data.source.remote.response.book.AddBookResponse
import com.katakerja.admin.core.data.source.remote.response.book.borrow.BorrowedBooksResponse
import com.katakerja.admin.core.data.source.remote.response.book.details.BookDetailsResponse
import com.katakerja.admin.core.data.source.remote.response.book.search.ListBooksResponse
import com.katakerja.admin.core.data.source.remote.response.book.wishlist.delete.DelWishlistBookResponse
import com.katakerja.admin.core.data.source.remote.response.book.wishlist.show.WishlistBooksResponse
import com.katakerja.admin.core.data.source.remote.response.book.wishlist.store.StoreWishlistResponse
import retrofit2.http.*

interface BookApiService {
    /* List Borrowed Book */
    @FormUrlEncoded
    @POST("books/store")
    suspend fun addBook(
        @Header("Authorization") authToken: String,
        @Field("isbn") isbn: String,
        @Field("judul") title: String,
        @Field("author") author: String,
        @Field("foto_buku") imgCover: String,
        @Field("tahun_terbit") releaseYear: Int,
        @Field("penerbit") publisher: String,
        @Field("kategori") category: String,
        @Field("stock") stock: Int,
        @Field("deskripsi") description: String,
    ): AddBookResponse

    /* List Borrowed Book */
    @GET("borrow/show/user/{id}")
    suspend fun getBorrowedBooksById(
        @Path("id") idUser: Int,
    ): BorrowedBooksResponse

    /* Details Book */
    @GET("books/show/{id}")
    suspend fun getBookDetailsById(
        @Path("id") bookId: Int,
    ): BookDetailsResponse

    /* Search Book*/
    @GET("books/search?")
    suspend fun getSearchedBooks(
        @Query("q") query: String,
    ): ListBooksResponse

    /* Search Book*/
    @GET("books/category/{cat}")
    suspend fun getBooksByCat(
        @Path("cat") category: String,
    ): ListBooksResponse

    /* Wishlist Book*/
    @GET("wish/show/user/{id}")
    suspend fun getWishBooksById(
        @Path("id") idUser: Int,
    ): WishlistBooksResponse

    @FormUrlEncoded
    @POST("wish/store")
    suspend fun insertWishlist(
        @Header("Authorization") authToken: String,
        @Field("user_id") userId: Int,
        @Field("book_id") bookId: Int,
    ): StoreWishlistResponse

    /* Wishlist Book*/
    @DELETE("wish/delete/{id}")
    suspend fun deleteWishlistBook(
        @Header("Authorization") authToken: String,
        @Path("id") idWish: Int,
    ): DelWishlistBookResponse
}