package com.anil.tailwebstask.landingPage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anil.tailwebstask.R
import com.anil.tailwebstask.landingPage.listeners.EditListenerInterface
import com.anil.tailwebstask.signInPage.entityModel.Marks

class MainActivityAdapter(private var listItem: List<Marks>,
                          private var editListenerInterface: EditListenerInterface):
    RecyclerView.Adapter<MainActivityAdapter.MyViewHolder>(){

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameTv: TextView = view.findViewById(R.id.rv_name)
        val subjectTv: TextView = view.findViewById(R.id.rv_subject)
        val marksTv: TextView = view.findViewById(R.id.rv_marks)
        val editBtn: ImageView = view.findViewById(R.id.edit_row_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        listItem[position]?.let { itemModel ->
            with(holder){
                nameTv.text = itemModel.name
                subjectTv.text = itemModel.subject
                marksTv.text = " " + itemModel.marks
                editBtn.setOnClickListener {
                    editListenerInterface.onEditClick(itemModel)
                }
            }
        }
    }

    fun submitList(listItem: List<Marks>) {
        this.listItem = listItem
        notifyDataSetChanged()
    }
}