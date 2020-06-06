package com.anil.tailwebstask.startUpPage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anil.tailwebstask.landingPage.MainActivity
import com.anil.tailwebstask.R
import com.anil.tailwebstask.utilities.PrefManager
import com.anil.tailwebstask.utilities.UtilConstants
import com.anil.tailwebstask.welcomeScreen.WelcomeScreen

class SplashScreen : AppCompatActivity() {

    private lateinit var prefManager: PrefManager

    private fun initialise(){
        prefManager = PrefManager(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initialise()
        openApp()
    }

    private fun checkSession(): Boolean {
        return prefManager.getBool(UtilConstants.SESSION, false)
    }

    private fun openApp() {
        val intent: Intent
        if (checkSession()) {
            intent = Intent(this, MainActivity::class.java)
        } else {
            intent = Intent(this, WelcomeScreen::class.java)
        }
        startActivity(intent)
        finish()
    }
}
