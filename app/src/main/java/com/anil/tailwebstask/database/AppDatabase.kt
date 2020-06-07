package com.anil.tailwebstask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anil.tailwebstask.signInPage.entityModel.Marks
import com.anil.tailwebstask.signInPage.entityModel.User

@Database(entities = [User::class, Marks::class],
    version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun signUpDao(): SignUpDao
    abstract fun addSubjectDao(): AddSubjectDao
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,"app_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}