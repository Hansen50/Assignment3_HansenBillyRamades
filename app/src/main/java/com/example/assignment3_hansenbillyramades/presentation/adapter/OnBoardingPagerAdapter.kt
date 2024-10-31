package com.example.assignment3_hansenbillyramades.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private val fragments = ArrayList<Fragment>()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}

