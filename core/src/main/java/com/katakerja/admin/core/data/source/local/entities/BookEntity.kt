package com.katakerja.admin.core.data.source.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_entity")
data class BookEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,
)