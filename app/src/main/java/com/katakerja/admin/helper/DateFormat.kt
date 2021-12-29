package com.katakerja.admin.helper

import java.text.SimpleDateFormat
import java.util.*

object DateFormat {
    fun fullDateToFormalDate(date: String): String {
        val formatDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault()).parse(date)
        return if (formatDate != null) {
            val finalDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(formatDate)
            val finalDate = SimpleDateFormat("dd", Locale.getDefault()).format(formatDate)
            val finalMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(formatDate)
            val finalYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(formatDate)

            "$finalDay, $finalDate $finalMonth $finalYear"
        } else {
            ""
        }

    }
}