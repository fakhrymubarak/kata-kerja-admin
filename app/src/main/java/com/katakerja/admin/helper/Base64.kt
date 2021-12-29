package com.katakerja.admin.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object Base64 {
    fun decode(base64String: String): Bitmap {
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}