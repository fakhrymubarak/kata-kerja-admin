package com.katakerja.admin.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.katakerja.admin.adapter.SectionsPagerAdapter
import com.katakerja.admin.databinding.ActivityOnBoardingBinding
import com.katakerja.admin.ui.login.LoginActivity
import com.katakerja.admin.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private var obPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpOnBoarding.adapter = SectionsPagerAdapter(this)
        binding.dotsIndicator.setViewPager2(binding.vpOnBoarding)

        populateView()
        binding.apply {
            btnSkip.setOnClickListener { }
            btnBackOb.setOnClickListener {
                obPosition -= 1
                populateView()
            }
            btnNextOb.setOnClickListener {
                obPosition += 1
                populateView()
            }
            btnSkip.setOnClickListener { intentTo(LoginActivity::class.java) }
            btnLogin.setOnClickListener { intentTo(LoginActivity::class.java) }
            btnRegister.setOnClickListener { intentTo(RegisterActivity::class.java) }

            vpOnBoarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    obPosition = position
                    populateView()
                }
            })
        }
    }

    private fun <T> intentTo(destination: Class<T>) {
        val intent = Intent(this, destination)
        startActivity(intent)
        finish()
    }

    private fun populateView() {
        binding.apply {
            vpOnBoarding.setCurrentItem(obPosition, true)
            when (obPosition) {
                0 -> {
                    btnSkip.visibility = View.VISIBLE
                    btnBackOb.visibility = View.INVISIBLE
                    btnNextOb.visibility = View.VISIBLE
                    btnLogin.visibility = View.INVISIBLE
                    btnRegister.visibility = View.INVISIBLE
                }
                1 -> {
                    btnSkip.visibility = View.VISIBLE
                    btnBackOb.visibility = View.VISIBLE
                    btnNextOb.visibility = View.VISIBLE
                    btnLogin.visibility = View.INVISIBLE
                    btnRegister.visibility = View.INVISIBLE
                }
                2 -> {
                    btnSkip.visibility = View.INVISIBLE
                    btnBackOb.visibility = View.INVISIBLE
                    btnNextOb.visibility = View.INVISIBLE
                    btnLogin.visibility = View.VISIBLE
                    btnRegister.visibility = View.VISIBLE
                }
            }
        }
    }
}