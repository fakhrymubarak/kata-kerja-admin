package com.katakerja.admin.ui.profile.password

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.katakerja.admin.R
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentChangePasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePassword : Fragment(R.layout.fragment_change_password) {
    private val binding by viewBinding(FragmentChangePasswordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}