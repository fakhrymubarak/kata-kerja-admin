package com.katakerja.admin.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.katakerja.admin.const.Settings
import com.katakerja.admin.ui.dashboard.MainActivity
import com.katakerja.admin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIsLogin()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val mode = prefs.getInt(Settings.KEY_THEMES_MODE, Settings.DEFAULT_THEMES_MODE)
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun checkIsLogin() {
        viewModel.getUserId.observe(this, { userId ->
            if (userId != 0) {
                intentTo(MainActivity::class.java)
            } else {
                intentTo(LoginActivity::class.java)
            }
        })
    }

    private fun <T> intentTo(destination: Class<T>) {
        val intent = Intent(this, destination)
        startActivity(intent)
        finish()
    }

}