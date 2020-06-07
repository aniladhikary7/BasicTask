package com.anil.tailwebstask.addSubject.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anil.tailwebstask.database.AddSubjectDao
import com.anil.tailwebstask.signInPage.UserEnum
import com.anil.tailwebstask.signInPage.entityModel.Marks
import com.anil.tailwebstask.utilities.AppExecutors
import com.anil.tailwebstask.utilities.PrefManager
import com.anil.tailwebstask.utilities.SingleLiveEvent
import com.anil.tailwebstask.utilities.UtilConstants

class AddSubjectRepository private constructor(private val addSubjectDao: AddSubjectDao,
                                                private val appExecutors: AppExecutors,
                                               private val prefManager: PrefManager){

    companion object {
        @Volatile
        private var instance: AddSubjectRepository? = null
        fun getInstance(
            addSubjectDao: AddSubjectDao,
            appExecutors: AppExecutors,
            prefManager: PrefManager
        ) =
            instance ?: synchronized(this) {
                instance ?: AddSubjectRepository(
                    addSubjectDao,
                    appExecutors,
                    prefManager
                )
                    .also { it }
            }
    }

    private val _dataFillStatus = MutableLiveData<SingleLiveEvent<UserEnum>>()
    val dataFillStatus: LiveData<SingleLiveEvent<UserEnum>>
        get() = _dataFillStatus

    fun insertUser(marks: Marks){
        if (marks.mobileNumber.isNotEmpty() &&
            marks.name.isNotEmpty()  &&
            marks.subject.isNotEmpty() &&
            marks.marks.isNotEmpty()){
            appExecutors.diskIO().execute {
                addSubjectDao.insert(marks)
                _dataFillStatus.postValue(SingleLiveEvent(UserEnum.SUCCESS))
            }
        }else{
            _dataFillStatus.postValue(SingleLiveEvent(UserEnum.FAILURE))
        }
    }

    fun fetchAllSubjects() = addSubjectDao.getListFromBB(prefManager.getString(
        UtilConstants.USER_PHONE_NUMBER,""))
}