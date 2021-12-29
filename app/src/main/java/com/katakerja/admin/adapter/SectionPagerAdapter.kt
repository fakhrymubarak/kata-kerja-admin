package com.katakerja.admin.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.katakerja.admin.ui.onboarding.OnBoardingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt(EXTRA_PAGE, position)
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = OnBoardingFragment()
            1 -> fragment = OnBoardingFragment()
            2 -> fragment = OnBoardingFragment()
        }
        fragment?.arguments = bundle
        return fragment as Fragment
    }

    companion object {
        const val EXTRA_PAGE = "extra_page"
    }
}