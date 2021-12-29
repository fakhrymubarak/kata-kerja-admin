package com.katakerja.admin.ui.profile.about

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.katakerja.admin.BuildConfig
import com.katakerja.admin.R
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentAboutBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {
    private val binding by viewBinding(FragmentAboutBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivLogo.load(R.mipmap.img_logo_light) {
                transformations(RoundedCornersTransformation(16.0F))
            }
            tvAppVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
            tvCopyright.text =
                getString(R.string.copyright, Calendar.getInstance().get(Calendar.YEAR))
            btnOssLicense.setOnClickListener {
                startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
            }

        }
    }
}