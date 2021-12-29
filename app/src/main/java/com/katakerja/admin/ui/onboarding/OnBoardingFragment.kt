package com.katakerja.admin.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.katakerja.admin.R
import com.katakerja.admin.adapter.SectionsPagerAdapter
import com.katakerja.admin.core.utils.viewBinding
import com.katakerja.admin.databinding.FragmentOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {
    private val binding by viewBinding(FragmentOnBoardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { populateView(it.getInt(SectionsPagerAdapter.EXTRA_PAGE, 0)) }
    }

    private fun populateView(page: Int) {
        val drawableImage = AppCompatResources.getDrawable(
            requireContext(),
            when (page) {
                1 -> R.drawable.illust_ob1
                2 -> R.drawable.illust_ob2
                else -> R.drawable.illust_ob3
            }
        )
        val title = getString(
            when (page) {
                1 -> R.string.title_ob1
                2 -> R.string.title_ob2
                else -> R.string.title_ob3
            }
        )
        val desc = getString(
            when (page) {
                1 -> R.string.desc_ob1
                2 -> R.string.desc_ob2
                else -> R.string.desc_ob3
            }
        )
        binding.apply {
            imageView.setImageDrawable(drawableImage)
            tvOnboardingTitle.text = title
            tvOnboardingDesc.text = desc
        }
    }
}