package com.katakerja.admin.helper

import android.app.DatePickerDialog
import android.content.Context
import android.view.MotionEvent
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

object Converter {
    fun TextInputEditText.transformIntoDatePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat(format, Locale.getDefault())
                setText(sdf.format(myCalendar.time))
            }

        setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    DatePickerDialog(
                        context, datePickerOnDataSetListener, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).run {
                        maxDate?.time?.also { datePicker.maxDate = it }
                        show()
                    }
                }
                MotionEvent.ACTION_UP -> v.performClick()
            }
            true
        }
    }
}