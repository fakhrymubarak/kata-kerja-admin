package com.katakerja.admin.di

import com.katakerja.admin.core.domain.usecase.book.BookInteractor
import com.katakerja.admin.core.domain.usecase.book.BookUseCase
import com.katakerja.admin.core.domain.usecase.user.UserInteractor
import com.katakerja.admin.core.domain.usecase.user.UserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase

    @Binds
    @Singleton
    abstract fun provideBookUseCase(bookInteractor: BookInteractor): BookUseCase
}