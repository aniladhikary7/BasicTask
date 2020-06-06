package com.anil.tailwebstask.welcomeScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.anil.tailwebstask.R
import com.google.android.material.tabs.TabLayout


class WelcomeScreen : AppCompatActivity() {

    private lateinit var tabIndicator: TabLayout
    private lateinit var sliderAdapter: WelcomeViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private fun initialise(){
        viewPager = findViewById(R.id.screen_viewpager)
        tabIndicator = findViewById(R.id.indicator_tabLayout)

        sliderAdapter = WelcomeViewPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )

        viewPager.adapter = sliderAdapter
        tabIndicator.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            override fun onPageSelected(position: Int) {
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        initialise()
    }
}
