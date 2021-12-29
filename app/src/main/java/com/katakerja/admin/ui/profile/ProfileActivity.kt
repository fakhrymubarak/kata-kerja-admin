package com.katakerja.admin.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.katakerja.admin.R
import com.katakerja.admin.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_profile)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.tvActivityTitle.text = destination.label
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}