package com.anil.tailwebstask.signInPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.anil.tailwebstask.R
import com.anil.tailwebstask.utilities.UtilConstants

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val fragmentType = intent.getStringExtra(UtilConstants.INTENT_START_FRAGMENT)

        if (savedInstanceState == null) {
            val ft: FragmentTransaction= supportFragmentManager.beginTransaction();
            ft.replace(R.id.fragment_container, SignUpFragment.newInstance(fragmentType))
            ft.commit();
        }
    }
}
