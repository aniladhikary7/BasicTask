package com.anil.tailwebstask.utilities

import android.content.Context
import com.anil.tailwebstask.addSubject.repo.AddSubjectRepository
import com.anil.tailwebstask.addSubject.viewModel.AddSubjectViewModelFactory
import com.anil.tailwebstask.database.AppDatabase
import com.anil.tailwebstask.signInPage.entityModel.User
import com.anil.tailwebstask.signInPage.repo.SignUpRepository
import com.anil.tailwebstask.signInPage.viewModel.SignUpViewModelFactory

object InjectUtils {

    private fun getSignUpRepository(context: Context): SignUpRepository {
        return SignUpRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).signUpDao(),
            AppExecutors.getInstance())
    }

    fun provideSignUpViewModelFactory(context: Context): SignUpViewModelFactory {
        val repository = getSignUpRepository(context)
        return SignUpViewModelFactory(repository)
    }

    private fun getAddSubjectRepository(context: Context): AddSubjectRepository {
        return AddSubjectRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).addSubjectDao(),
            AppExecutors.getInstance(),
            PrefManager.getInstance(context.applicationContext))
    }

    fun provideAddSubjectViewModelFactory(context: Context): AddSubjectViewModelFactory {
        val repository = getAddSubjectRepository(context)
        return AddSubjectViewModelFactory(repository)
    }
}