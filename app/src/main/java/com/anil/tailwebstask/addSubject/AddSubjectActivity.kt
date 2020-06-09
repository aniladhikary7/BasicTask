package com.anil.tailwebstask.addSubject

import android.os.Bundle
import android.text.Editable
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
import com.google.android.material.textfield.TextInputLayout

class AddSubjectActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var mainView: CoordinatorLayout
    private lateinit var toolbar: Toolbar
    private lateinit var addNameLayout: TextInputLayout
    private lateinit var addSubjectLayout: TextInputLayout
    private lateinit var addMarksLayout: TextInputLayout
    private lateinit var addName: TextInputEditText
    private lateinit var addSubject: TextInputEditText
    private lateinit var addMarks: TextInputEditText
    private lateinit var submitBtn: Button
    private lateinit var updateBtn: Button
    private lateinit var prefManager: PrefManager

    private var setScreen = ""
    private var intentName = ""
    private var intentSubject = ""
    private var intentScore = ""

    private val viewModel: AddSubjectViewModel by viewModels {
        InjectUtils.provideAddSubjectViewModelFactory(this)
    }

    private fun initViews(){
        mainView = findViewById(R.id.add_subject_main_view)
        toolbar = findViewById(R.id.notification_center_toolbar)
        addNameLayout = findViewById(R.id.student_name_layout)
        addSubjectLayout = findViewById(R.id.subject_layout)
        addMarksLayout = findViewById(R.id.marks_layout)
        addName = findViewById(R.id.student_name_et)
        addSubject = findViewById(R.id.subject_et)
        addMarks = findViewById(R.id.marks_et)
        submitBtn = findViewById(R.id.submit_btn)
        updateBtn = findViewById(R.id.update_btn)
        submitBtn.setOnClickListener(this)
        updateBtn.setOnClickListener(this)
    }

    private fun initialise(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = resources.getString(R.string.add_details)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setNavigationOnClickListener { finish() }
        prefManager = PrefManager(this)
        if (setScreen == "update"){
            addNameLayout.visibility = View.GONE
            addSubjectLayout.visibility = View.GONE
            addMarksLayout.visibility = View.VISIBLE
            submitBtn.visibility = View.GONE
            updateBtn.visibility = View.VISIBLE
            addMarks.text = Editable.Factory.getInstance().newEditable(intentScore)
        }else{
            addNameLayout.visibility = View.VISIBLE
            addSubjectLayout.visibility = View.VISIBLE
            addMarksLayout.visibility = View.VISIBLE
            submitBtn.visibility = View.VISIBLE
            updateBtn.visibility = View.GONE
            addName.text = Editable.Factory.getInstance().newEditable(intentName)
            addSubject.text = Editable.Factory.getInstance().newEditable(intentSubject)
            addMarks.text = Editable.Factory.getInstance().newEditable(intentScore)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)
        setScreen = intent.getStringExtra(UtilConstants.INTENT_START_UPDATE)
        intentName = intent.getStringExtra(UtilConstants.INTENT_START_NAME)
        intentSubject = intent.getStringExtra(UtilConstants.INTENT_START_SUBJECT)
        intentScore = intent.getStringExtra(UtilConstants.INTENT_START_SCORE)

        initViews()
        initialise()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit_btn -> {
                addSubject()
            }
            R.id.update_btn -> {
                updateSubjectMarks()
            }
        }
    }

    private fun addSubject(){
        var name: String = addName.text.toString().trim()
        var subject: String = addSubject.text.toString().trim()
        var score: Int = Integer.parseInt(addMarks.text.toString().trim())
        var phoneNumber: String = ""+prefManager.getString(UtilConstants
            .USER_PHONE_NUMBER, "1234")
        viewModel.fetchMatchedRow(name, subject, phoneNumber,score)

        viewModel.fetchDataFillStatus().observe(this, Observer {
            when (it.getContentIfNotHandled()) {
                UserEnum.SUCCESS -> {
                    finish()
                }
                UserEnum.UPDATE -> {
                    finish()
                }
                UserEnum.FAILURE -> {
                    showSnackBar(resources.getString(R.string.enter_fields))
                }
            }
        })
    }

    private fun updateSubjectMarks(){
        var score: Int = Integer.parseInt(addMarks.text.toString().trim())
        viewModel.fetchUpdateRow(score, intentName, intentSubject)

        viewModel.fetchUpdateStatus().observe(this, Observer {
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
