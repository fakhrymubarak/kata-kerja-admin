package com.katakerja.admin.core.di

import android.content.Context
import androidx.room.Room
import com.katakerja.admin.core.data.source.local.room.KatakerjaDao
import com.katakerja.admin.core.data.source.local.room.KatakerjaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    private val passphrase: ByteArray = SQLiteDatabase.getBytes("katakerja_db_uin".toCharArray())
    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): KatakerjaDatabase =
        Room.databaseBuilder(
            context,
            KatakerjaDatabase::class.java, "Katakerja.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()

    @Provides
    fun provideKatakerjaDao(database: KatakerjaDatabase): KatakerjaDao =
        database.katakerjaDao()
}