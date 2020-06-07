package com.anil.tailwebstask.addSubject

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import com.anil.tailwebstask.R
import com.anil.tailwebstask.addSubject.viewModel.AddSubjectViewModel
import com.anil.tailwebstask.signInPage.UserEnum
import com.anil.tailwebstask.signInPage.entityModel.Marks
import com.anil.tailwebstask.utilities.InjectUtils
import com.anil.tailwebstask.utilities.PrefManager
import com.anil.tailwebstask.utilities.UtilConstants
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class AddSubjectActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var mainView: CoordinatorLayout
    private lateinit var toolbar: Toolbar
    private lateinit var addName: TextInputEditText
    private lateinit var addSubject: TextInputEditText
    private lateinit var addMarks: TextInputEditText
    private lateinit var submitBtn: Button
    private lateinit var prefManager: PrefManager
    private val viewModel: AddSubjectViewModel by viewModels {
        InjectUtils.provideAddSubjectViewModelFactory(this)
    }

    private fun initViews(){
        mainView = findViewById(R.id.add_subject_main_view)
        toolbar = findViewById(R.id.notification_center_toolbar)
        addName = findViewById(R.id.student_name_et)
        addSubject = findViewById(R.id.subject_et)
        addMarks = findViewById(R.id.marks_et)
        submitBtn = findViewById(R.id.submit_btn)
        submitBtn.setOnClickListener(this)
    }

    private fun initialise(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = resources.getString(R.string.add_details)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setNavigationOnClickListener { finish() }
        prefManager = PrefManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)

        initViews()
        initialise()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit_btn -> {
                addSubject()
            }
        }
    }

    private fun addSubject(){
        var name: String = addName.text.toString().trim()
        var subject: String = addSubject.text.toString().trim()
        var score: String = addMarks.text.toString().trim()
        var phoneNumber: String = ""+prefManager.getString(UtilConstants
            .USER_PHONE_NUMBER, "1234")
        var marks = Marks(subject,phoneNumber,name,score)
        viewModel.addASubject(marks)

        viewModel.fetchDataFillStatus().observe(this, Observer {
            when (it.getContentIfNotHandled()) {
                UserEnum.SUCCESS -> {
                    finish()
                }
                UserEnum.FAILURE -> {
                    showSnackBar(resources.getString(R.string.enter_fields))
                }
            }
        })
    }

    private fun showSnackBar(message: String) {
        try {
            Snackbar.make(mainView, message, Snackbar.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
        }
    }
}
