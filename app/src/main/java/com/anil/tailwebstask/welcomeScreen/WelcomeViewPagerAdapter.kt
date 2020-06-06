package com.anil.tailwebstask.welcomeScreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class WelcomeViewPagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return SliderItemFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 3
    }
}