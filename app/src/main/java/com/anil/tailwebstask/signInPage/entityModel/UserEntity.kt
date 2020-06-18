package com.anil.tailwebstask.signInPage.entityModel

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "signUp_user")
data class User(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "mobile_number")
    val mobileNumber: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "password")
    val password: String
)

@Entity(tableName = "marks_table",
    foreignKeys = arrayOf(
        ForeignKey(entity = User::class,
            parentColumns = arrayOf("mobile_number"),
            childColumns = arrayOf("mobile_number"),
            onDelete = ForeignKey.CASCADE)))
data class Marks(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subjectId")
    var subjectId: Int = 0,
    @ColumnInfo(name = "subject")
    val subject: String,
    @ColumnInfo(name = "mobile_number")
    val mobileNumber: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "marks")
    val marks: String
){
    constructor(subject: String, mobileNumber: String, name: String, marks: String):
            this(0, subject, mobileNumber,name,marks)
}