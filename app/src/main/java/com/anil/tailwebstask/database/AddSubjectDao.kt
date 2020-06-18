package com.anil.tailwebstask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.anil.tailwebstask.signInPage.entityModel.Marks

@Dao
interface AddSubjectDao {
    @Insert
    fun insert(marks: Marks)

    @Query("SELECT * FROM marks_table WHERE mobile_number = :mobileNumber")
    fun getListFromBB(mobileNumber: String?): LiveData<List<Marks>>

    @Query("SELECT * FROM marks_table WHERE mobile_number = :mobileNumber AND subject = :subject")
    fun getMatchedRow(mobileNumber: String, subject: String) : Marks

    @Update
    fun updateMarkRow(marks: Marks)

    @Query("UPDATE marks_table SET marks = :marks WHERE name = :name AND subject = :subject")
    fun updateMarks(marks: Int, name: String, subject: String)
}