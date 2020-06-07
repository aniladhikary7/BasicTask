package com.anil.tailwebstask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anil.tailwebstask.signInPage.entityModel.User

@Dao
interface SignUpDao {
    @Insert
    fun insert(user: User)
    @Query("SELECT * FROM signUp_user")
    fun getAllUserFromBB(): LiveData<List<User>>
    @Query("SELECT * FROM signUp_user WHERE mobile_number = :mobileNumber AND password = :password")
    fun getUser(mobileNumber: String, password: String) : User
}