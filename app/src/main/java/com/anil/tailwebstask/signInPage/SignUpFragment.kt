package com.anil.tailwebstask.signInPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anil.tailwebstask.R
import com.anil.tailwebstask.landingPage.MainActivity
import com.anil.tailwebstask.signInPage.entityModel.User
import com.anil.tailwebstask.signInPage.viewModel.SignUpViewModel
import com.anil.tailwebstask.utilities.InjectUtils
import com.anil.tailwebstask.utilities.PrefManager
import com.anil.tailwebstask.utilities.UtilConstants
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : Fragment(), View.OnClickListener {

    private lateinit var prefManager: PrefManager
    private lateinit var mainView: ScrollView
    private lateinit var fragmentType: String
    private lateinit var nameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var numberLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var nameEt: TextInputEditText
    private lateinit var emailEt: TextInputEditText
    private lateinit var numberEt: TextInputEditText
    private lateinit var passwordEt: TextInputEditText
    private lateinit var signUpBtn: Button
    private lateinit var logInBtn: Button
    private val viewModel: SignUpViewModel by viewModels {
        InjectUtils.provideSignUpViewModelFactory(requireContext())
    }

    companion object{
        private const val FRAGMENT_TYPE = "fragment_type"
        @JvmStatic
        fun newInstance(fragType: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(FRAGMENT_TYPE, fragType)
                }
            }
    }

    private fun initViews(view: View){
        mainView = view.findViewById(R.id.scroll_view)
        nameLayout = view.findViewById(R.id.name_layout)
        emailLayout = view.findViewById(R.id.email_layout)
        numberLayout = view.findViewById(R.id.number_layout)
        passwordLayout = view.findViewById(R.id.password_layout)
        nameEt = view.findViewById(R.id.name_et)
        emailEt = view.findViewById(R.id.email_et)
        numberEt = view.findViewById(R.id.number_et)
        passwordEt = view.findViewById(R.id.password_et)
        signUpBtn = view.findViewById(R.id.sign_up_btn)
        logInBtn = view.findViewById(R.id.log_in_btn)

        signUpBtn.setOnClickListener(this)
        logInBtn.setOnClickListener(this)
    }

    private fun initialise(view: View){
        prefManager = PrefManager(view.context)
        if (fragmentType == "logIn"){
            nameLayout.visibility = View.GONE
            emailLayout.visibility = View.GONE
            numberLayout.visibility = View.VISIBLE
            passwordLayout.visibility = View.VISIBLE
            signUpBtn.visibility = View.GONE
            logInBtn.visibility = View.VISIBLE
        }else{
            nameLayout.visibility = View.VISIBLE
            emailLayout.visibility = View.VISIBLE
            nameLayout.visibility = View.VISIBLE
            passwordLayout.visibility = View.VISIBLE
            signUpBtn.visibility = View.VISIBLE
            logInBtn.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_sign_up, container, false)
        fragmentType = arguments?.getString(FRAGMENT_TYPE) ?: ""
        initViews(v)
        initialise(v)
        return v
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sign_up_btn -> {
                saveUser()
            }
            R.id.log_in_btn -> {
                findUser()
            }
        }
    }

    private fun saveUser(){
        val name: String = nameEt.text?.trim().toString()
        val email: String = emailEt.text?.trim().toString()
        val phone: String = numberEt.text?.trim().toString()
        val password: String = passwordEt.text?.trim().toString()
        var user = User(phone,name,email,password)
        viewModel.saveUserInfo(user)

        viewModel.fetchValidationStatus().observe(viewLifecycleOwner, Observer {
            when (it.getContentIfNotHandled()) {
                UserEnum.SUCCESS -> {
                    prefManager.setBool(UtilConstants.SESSION, true)
                    prefManager.setString(UtilConstants.USER_PHONE_NUMBER, phone)
                    startActivity(Intent(context, MainActivity::class.java))
                }
                UserEnum.EMPTY ->{
                    showSnackBar(resources.getString(R.string.enter_fields))
                }
                UserEnum.VALID_EMAIL -> {
                    showSnackBar(resources.getString(R.string.invalid_mail))
                }
                UserEnum.VALID_NUMBER -> {
                    showSnackBar(resources.getString(R.string.invalid_number))
                }
            }
        })
    }

    private fun findUser(){
        val number: String = numberEt.text?.trim().toString()
        val password: String = passwordEt.text?.trim().toString()
        viewModel.searchUser(number, password)

        viewModel.fetchFindUserStatus().observe(viewLifecycleOwner, Observer {
            when (it.getContentIfNotHandled()) {
                UserEnum.SUCCESS -> {
                    prefManager.setBool(UtilConstants.SESSION, true)
                    prefManager.setString(UtilConstants.USER_PHONE_NUMBER, "" + number)
                    startActivity(Intent(context, MainActivity::class.java))
                }
                UserEnum.EMPTY -> {
                    showSnackBar(resources.getString(R.string.wrong_credentials))
                }
                UserEnum.FAILURE -> {
                    showSnackBar(resources.getString(R.string.enter_fields))
                }
            }
        })
    }

    private fun showSnackBar(message: String) =
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()

}
