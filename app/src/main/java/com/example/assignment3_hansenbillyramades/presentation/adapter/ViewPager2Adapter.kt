package com.example.assignment3_hansenbillyramades.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.assignment3_hansenbillyramades.presentation.view.fragment.ExploreFragment
import com.example.assignment3_hansenbillyramades.presentation.view.fragment.ItineraryFragment
import com.example.assignment3_hansenbillyramades.presentation.view.fragment.ProfileFragment

class ViewPager2Adapter (fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ExploreFragment()
            1 -> ItineraryFragment()
            2 -> ProfileFragment()
            else -> throw IllegalStateException("Invalid tab position ${position}")
        }
    }

}