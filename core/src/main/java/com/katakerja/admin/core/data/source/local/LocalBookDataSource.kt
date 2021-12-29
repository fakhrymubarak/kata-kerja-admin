package com.katakerja.admin.core.data.source.local

import com.katakerja.admin.core.data.source.local.room.KatakerjaDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalBookDataSource @Inject constructor(private val mKatakerjaDao: KatakerjaDao) {

}