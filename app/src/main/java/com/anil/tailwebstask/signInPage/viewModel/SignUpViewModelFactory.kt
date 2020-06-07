package com.anil.tailwebstask.signInPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anil.tailwebstask.signInPage.repo.SignUpRepository

class SignUpViewModelFactory(private val signUpRepository: SignUpRepository):
ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        SignUpViewModel(signUpRepository) as T
}