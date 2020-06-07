package com.anil.tailwebstask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anil.tailwebstask.signInPage.entityModel.Marks

@Dao
interface AddSubjectDao {
    @Insert
    fun insert(marks: Marks)
    @Query("SELECT * FROM marks_table WHERE mobile_number = :mobileNumber")
    fun getListFromBB(mobileNumber: String?): LiveData<List<Marks>>
    @Query("SELECT * FROM marks_table WHERE name = :name AND subject = :subject")
    fun getUser(name: String, subject: String) : Marks
}