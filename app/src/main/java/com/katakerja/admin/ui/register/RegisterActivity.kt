package com.katakerja.admin.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.katakerja.admin.R
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.databinding.ActivityRegisterBinding
import com.katakerja.admin.helper.Converter.transformIntoDatePicker
import com.katakerja.admin.helper.FormValidator.editTextIsNotEmpty
import com.katakerja.admin.helper.FormValidator.emailFormatMatched
import com.katakerja.admin.helper.FormValidator.passwordFormatMatched
import com.katakerja.admin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvLogin.setOnClickListener { intentTo(LoginActivity::class.java) }
            etBornDate.transformIntoDatePicker(this@RegisterActivity, "yyyy-MM-dd", Date())

            btnRegister.setOnClickListener {
                if (tilName.editTextIsNotEmpty() &&
                    tilEmail.editTextIsNotEmpty() && tilEmail.emailFormatMatched() &&
                    tilPassword.editTextIsNotEmpty() && tilPassword.passwordFormatMatched() &&
                    tilBornDate.editTextIsNotEmpty() &&
                    tilPhoneNumber.editTextIsNotEmpty()
                ) {
                    val name = etName.text.toString()
                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()
                    val bornDate = etBornDate.text.toString()
                    val phoneNumber = etPhoneNumber.text.toString()

                    vmPostRegister(name, email, password, bornDate, phoneNumber)
                }
            }
            btnRegisterGoogle.setOnClickListener {}
        }

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun vmPostRegister(
        name: String,
        email: String,
        password: String,
        bornDate: String,
        phoneNumber: String
    ) {
        viewModel.postRegister(email, password, name, bornDate, phoneNumber)
            .observe(this, { registerResource ->
                if (registerResource != null) {
                    when (registerResource) {
                        is Resource.Loading -> setRegisterLoading(true)
                        is Resource.Success -> {
                            setRegisterLoading(false)
                            intentTo(LoginActivity::class.java)
                            Toast.makeText(
                                this@RegisterActivity,
                                getString(R.string.please_login),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is Resource.Error -> {
                            setRegisterLoading(false)
                            Toast.makeText(
                                this@RegisterActivity,
                                registerResource.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
    }

    private fun <T> intentTo(destination: Class<T>) {
        val intent = Intent(this, destination)
        startActivity(intent)
        finish()
    }


    private fun setRegisterLoading(state: Boolean) {
        enableBtnRegister(state)
        binding.pbRegister.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun enableBtnRegister(state: Boolean) {
        binding.apply {
            btnRegister.isEnabled = !state
            if (state) {
                btnRegister.background = AppCompatResources.getDrawable(
                    this@RegisterActivity,
                    R.drawable.shape_rec_fill_gray_light
                )
            } else {
                btnRegister.background = AppCompatResources.getDrawable(
                    this@RegisterActivity,
                    R.drawable.shape_rec_fill_blue
                )
            }
        }
    }
}