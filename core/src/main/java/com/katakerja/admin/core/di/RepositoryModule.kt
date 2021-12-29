package com.katakerja.admin.core.di

import com.katakerja.admin.core.data.source.repository.BookRepository
import com.katakerja.admin.core.data.source.repository.UserRepository
import com.katakerja.admin.core.domain.repository.IBookRepository
import com.katakerja.admin.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(userRepository: UserRepository): IUserRepository

    @Binds
    abstract fun provideBookRepository(bookRepository: BookRepository): IBookRepository
}