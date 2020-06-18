package com.anil.tailwebstask.signInPage.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anil.tailwebstask.database.SignUpDao
import com.anil.tailwebstask.signInPage.UserEnum
import com.anil.tailwebstask.signInPage.entityModel.User
import com.anil.tailwebstask.utilities.AppExecutors
import com.anil.tailwebstask.utilities.SingleLiveEvent

class SignUpRepository private constructor(
    private val signUpDao: SignUpDao,
    private val appExecutors: AppExecutors
) {

    companion object {
        @Volatile
        private var instance: SignUpRepository? = null
        fun getInstance(
            signUpDao: SignUpDao,
            appExecutors: AppExecutors
        ) =
            instance ?: synchronized(this) {
                instance ?: SignUpRepository(
                    signUpDao,
                    appExecutors
                )
                    .also { it }
            }
    }

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    private val _validationStatus = MutableLiveData<SingleLiveEvent<UserEnum>>()
    val validationStatus: LiveData<SingleLiveEvent<UserEnum>>
        get() = _validationStatus

    fun validation(user: User) {
        if (user.mobileNumber.isNotEmpty() &&
            user.email.isNotEmpty() &&
            user.name.isNotEmpty() &&
            user.password.isNotEmpty()
        ) {
            if (validEmail(user.email)) {
                if (validNumber(user.mobileNumber)) {
                    appExecutors.diskIO().execute {
                        signUpDao.insert(user)
                        _validationStatus.postValue(SingleLiveEvent(UserEnum.SUCCESS))
                    }
                } else {
                    _validationStatus.postValue(SingleLiveEvent(UserEnum.VALID_NUMBER))
                }
            } else {
                _validationStatus.postValue(SingleLiveEvent(UserEnum.VALID_EMAIL))
            }
        } else {
            _validationStatus.postValue(SingleLiveEvent(UserEnum.EMPTY))
        }
    }

    private fun validEmail(email: String): Boolean {
        return email.matches(emailPattern)
    }

    private fun validNumber(number: String): Boolean {
        return number.length == 10
    }

    fun findUserInDB(number: String, password: String) {
        if (number.isNotEmpty() &&
            password.isNotEmpty()
        ) {
            appExecutors.diskIO().execute {
                val user: User = signUpDao.getUser(number, password)
                if (!user?.mobileNumber.isNullOrEmpty() && !user?.password.isNullOrEmpty()) {
                    _findUserLiveData.postValue(SingleLiveEvent(UserEnum.SUCCESS))
                } else {
                    _findUserLiveData.postValue(SingleLiveEvent(UserEnum.EMPTY))
                }
            }
        } else {
            _findUserLiveData.postValue(SingleLiveEvent(UserEnum.FAILURE))
        }
    }

    private val _findUserLiveData = MutableLiveData<SingleLiveEvent<UserEnum>>()
    val findUserLiveData: LiveData<SingleLiveEvent<UserEnum>>
        get() = _findUserLiveData
}
