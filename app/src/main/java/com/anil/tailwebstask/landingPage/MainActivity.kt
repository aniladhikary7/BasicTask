package com.anil.tailwebstask.landingPage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anil.tailwebstask.R
import com.anil.tailwebstask.addSubject.AddSubjectActivity
import com.anil.tailwebstask.addSubject.viewModel.AddSubjectViewModel
import com.anil.tailwebstask.landingPage.adapter.MainActivityAdapter
import com.anil.tailwebstask.signInPage.entityModel.Marks
import com.anil.tailwebstask.startUpPage.SplashScreen
import com.anil.tailwebstask.utilities.InjectUtils
import com.anil.tailwebstask.utilities.PrefManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , LifecycleOwner{

    private lateinit var emptyTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var prefManager: PrefManager
    private lateinit var myAdapter: MainActivityAdapter
    private var itemList: ArrayList<Marks> = ArrayList()
    private val viewModel: AddSubjectViewModel by viewModels {
        InjectUtils.provideAddSubjectViewModelFactory(this)
    }

    private fun initViews(){
        emptyTextView = findViewById(R.id.empty_text_view)
        recyclerView = findViewById(R.id.subject_list_rv)
    }

    private fun initialise(){
        prefManager = PrefManager(this)
        viewModel.getList.observe(this, Observer {
            myAdapter.submitList(it)
            if (it.isNotEmpty()){
                recyclerView.visibility = View.VISIBLE
                emptyTextView.visibility = View.GONE
            }else{
                recyclerView.visibility = View.GONE
                emptyTextView.visibility = View.VISIBLE
            }
        })

        myAdapter = MainActivityAdapter(itemList)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = myAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initialise()
        floatingButtonListener()
    }

    private fun floatingButtonListener(){
        fab.setOnClickListener { view ->
            startActivity(Intent(this, AddSubjectActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                displayExitAlert(resources.getString(R.string.are_you_sure))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayExitAlert(message: String) {
        val builder =
            AlertDialog.Builder(this)
        val alertDialog = builder.create()
        builder.setMessage(message)
            .setPositiveButton(
                resources.getString(R.string.dismiss)
            ) { dialog: DialogInterface?, which: Int -> alertDialog.dismiss() }
            .setNegativeButton(
                resources.getString(R.string.log_out)
            ) { dialog: DialogInterface?, which: Int ->
                prefManager.clearPref()
                val intent = Intent(this, SplashScreen::class.java)
                startActivity(intent)
                finishAffinity()
            }
            .setCancelable(false)
            .show()
    }
}
