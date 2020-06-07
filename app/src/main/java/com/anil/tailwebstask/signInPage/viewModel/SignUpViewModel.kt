package com.anil.tailwebstask.signInPage.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anil.tailwebstask.signInPage.entityModel.User
import com.anil.tailwebstask.signInPage.repo.SignUpRepository


class SignUpViewModel internal constructor(
    private var signUpRepository: SignUpRepository): ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    fun saveUserInfo(user: User) = signUpRepository.validation(user)

    fun searchUser(number: String, password: String) = signUpRepository.findUserInDB(number,password)

    fun fetchFindUserStatus() = signUpRepository.findUserLiveData

    fun fetchValidationStatus() = signUpRepository.validationStatus
}