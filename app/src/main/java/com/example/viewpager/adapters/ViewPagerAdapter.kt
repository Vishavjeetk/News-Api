package com.example.viewpager.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewpager.MainActivity
import com.example.viewpager.fragments.*

class ViewPagerAdapter(fragment: MainActivity): FragmentStateAdapter(fragment) {

    var item = arrayOf("General","Business","Technology","Science","Health","Sports","Entertainment")

    override fun getItemCount(): Int {
        return item.size
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return GeneralFragment()
            1 -> return BusinessFragment()
            2 -> return TechnologyFragment()
            3 -> return ScienceFragment()
            4 -> return HealthFragment()
            5 -> return SportsFragment()
            6 -> return EntertainmentFragment()
        }
        return GeneralFragment()
    }
}