package com.katakerja.admin.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.katakerja.admin.R
import com.katakerja.admin.core.data.Resource
import com.katakerja.admin.core.domain.model.User
import com.katakerja.admin.databinding.ActivityMainBinding
import com.katakerja.admin.ui.dashboard.home.HomeViewModel
import com.katakerja.admin.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUser()
        setNavFragment()
    }

    private fun setNavFragment() {
        val navView: BottomNavigationView = binding.navView

        viewModel.user.observe(this, { user ->
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            navView.setupWithNavController(navController)

            if (user.idRole == 4) {
                navController.navigate(R.id.action_navigation_home_to_navigation_unverified)
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        })
    }

    private fun getUser() {
        var userId = 0
        viewModel.getUserId().observe(this, { userId = it })
        viewModel.getAuthToken().observe(this, { token ->
            viewModel.getUserById(token, userId).observe(this, { userResource ->
                if (userResource != null) {
                    when (userResource) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            userResource.data?.let { populateUser(it) }
                        }
                        is Resource.Error -> {
                            Toast.makeText(this, userResource.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        })
    }

    private fun populateUser(user: User) {
        viewModel.user.postValue(user)
        binding.itemHeader.apply {
            tvName.text = user.name
            if (user.avatar == "") {
                ivProfile.load(R.drawable.ic_empty_avatar) {
                    transformations(CircleCropTransformation())
                }
            } else {
                ivProfile.load(user.avatar) { transformations(CircleCropTransformation()) }
            }

            ivProfile.setOnClickListener { intentTo(ProfileActivity::class.java, user) }
        }
    }

    private fun <T> intentTo(destination: Class<T>, user: User) {
        val intent = Intent(this, destination)
        intent.putExtra(ProfileActivity.EXTRA_USER, user)
        startActivity(intent)
    }
}