package com.katakerja.admin.ui.profile.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.katakerja.admin.R
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {
    private val binding by viewBinding(FragmentSettingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}