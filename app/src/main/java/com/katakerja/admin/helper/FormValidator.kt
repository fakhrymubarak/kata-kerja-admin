package com.katakerja.admin.helper

import android.util.Patterns
import com.katakerja.admin.R
import com.google.android.material.textfield.TextInputLayout

object FormValidator {
    fun TextInputLayout.editTextIsNotEmpty(text: String = editText?.text.toString()) =
        if (text == ""){
            error = editText?.context?.getString(R.string.error_empty_edit_text)
            editText?.requestFocus()
            false
        } else {
            error = null
            true

        }

    fun TextInputLayout.emailFormatMatched(email: String = editText?.text.toString()) =
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error = editText?.context?.getString(R.string.error_email_format)
            editText?.requestFocus()
            false
        } else {
            error = null
            true
        }

    fun TextInputLayout.passwordFormatMatched(password: String = editText?.text.toString()) =
        if (password.length < 6) {
            error = editText?.context?.getString(R.string.error_password_format)
            editText?.requestFocus()
            false
        } else {
            error = null
            true
        }
}