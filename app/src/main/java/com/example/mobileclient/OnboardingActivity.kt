package com.example.mobileclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mobileclient.fragments.Onboarding1Fragment
import com.example.mobileclient.fragments.Onboarding2Fragment
import com.example.mobileclient.fragments.Onboarding3Fragment

private const val NUM_PAGES = 3

class OnboardingActivity : FragmentActivity() {
    lateinit var mPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_layout)
        mPager = findViewById(R.id.pager)
        val pagerAdapter = PageFragmentAdapter(this)
        mPager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    private inner class PageFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> Onboarding1Fragment()
                1 -> Onboarding2Fragment()
                2 -> Onboarding3Fragment()
                else -> Onboarding1Fragment()
            }
        }
    }
}