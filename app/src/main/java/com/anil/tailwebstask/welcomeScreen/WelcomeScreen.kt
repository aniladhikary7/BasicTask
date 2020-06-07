package com.anil.tailwebstask.welcomeScreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.anil.tailwebstask.R
import com.anil.tailwebstask.signInPage.SignUpActivity
import com.anil.tailwebstask.utilities.UtilConstants
import com.google.android.material.tabs.TabLayout


class WelcomeScreen : AppCompatActivity(), View.OnClickListener {

    private lateinit var tabIndicator: TabLayout
    private lateinit var sliderAdapter: WelcomeViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var getStartBtn: Button
    private lateinit var loginBtn: TextView

    private fun initialise(){
        viewPager = findViewById(R.id.screen_viewpager)
        tabIndicator = findViewById(R.id.indicator_tabLayout)
        getStartBtn = findViewById(R.id.get_start_btn)
        loginBtn = findViewById(R.id.log_in_welcome_page)
        getStartBtn.setOnClickListener(this)
        loginBtn.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.get_start_btn -> {
                val signUpIntent = Intent(this, SignUpActivity::class.java)
                signUpIntent.putExtra(UtilConstants.INTENT_START_FRAGMENT, "getStart")
                startActivity(signUpIntent)
            }
            R.id.log_in_welcome_page -> {
                val logInIntent = Intent(this, SignUpActivity::class.java)
                logInIntent.putExtra(UtilConstants.INTENT_START_FRAGMENT, "logIn")
                startActivity(logInIntent)
            }
        }
    }
}
