package com.katakerja.admin.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.katakerja.admin.R
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.Login
import com.katakerja.admin.databinding.ActivityLoginBinding
import com.katakerja.admin.helper.FormValidator.editTextIsNotEmpty
import com.katakerja.admin.helper.FormValidator.emailFormatMatched
import com.katakerja.admin.helper.FormValidator.passwordFormatMatched
import com.katakerja.admin.ui.dashboard.MainActivity
import com.katakerja.admin.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                if (tilEmail.editTextIsNotEmpty() &&
                    tilEmail.emailFormatMatched() &&
                    tilPassword.editTextIsNotEmpty() &&
                    tilPassword.passwordFormatMatched()
                ) {
                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()
                    vmPostLogin(email, password)

                    this@LoginActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                }
            }
            tvRegister.setOnClickListener { intentTo(RegisterActivity::class.java) }
        }
    }

    private fun vmPostLogin(email: String, password: String) {
        viewModel.postLogin(email, password).observe(this@LoginActivity, { loginData ->
            if (loginData != null) {
                when (loginData) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        loginData.data?.let { data -> setDataStore(data) }
                        intentTo(MainActivity::class.java)
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setLoading(false)
                        Toast.makeText(
                            this@LoginActivity,
                            loginData.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun setDataStore(data: Login) {
        viewModel.saveAuthToken("Bearer ${data.token}")
        viewModel.saveUserId(data.id)
    }

    private fun setLoading(state: Boolean) {
        enableBtnLogin(state)
        binding.pbLogin.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun enableBtnLogin(state: Boolean) {
        binding.apply {
            btnLogin.isEnabled = !state
            if (state) {
                btnLogin.background = AppCompatResources.getDrawable(
                    this@LoginActivity,
                    R.drawable.shape_rec_fill_gray_light
                )
            } else {
                btnLogin.background = AppCompatResources.getDrawable(
                    this@LoginActivity,
                    R.drawable.shape_rec_fill_blue
                )
            }
        }

    }

    private fun <T> intentTo(destination: Class<T>) {
        val intent = Intent(this, destination)
        startActivity(intent)
        finish()
    }
}