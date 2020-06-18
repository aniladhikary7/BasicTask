package com.anil.tailwebstask.addSubject.repo

import android.util.Log
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

    fun insertSubject(name: String, subject: String, number: String, score: String){
        if (name.isNotEmpty() &&
            subject.isNotEmpty()  &&
            number.isNotEmpty() &&
            score.isNotEmpty()){
            appExecutors.diskIO().execute {
                val marks: Marks = addSubjectDao.getMatchedRow(number, subject, name)
                if (!marks?.mobileNumber.isNullOrEmpty() && !marks?.subject.isNullOrEmpty()) {
                    val objName: String = marks.name
                    val objSubject: String = marks.subject
                    val objNumber: String = marks.mobileNumber
                    val objScore: String = ""+marks.marks.toInt().plus(score.toInt())
                    val obj: Marks = Marks(objSubject, objNumber, objName, objScore)
                    addSubjectDao.updateMarkRow(obj)
                    _dataFillStatus.postValue(SingleLiveEvent(UserEnum.UPDATE))
                }else{
                    val obj = Marks(subject,number, name, score)
                    addSubjectDao.insert(obj)
                    _dataFillStatus.postValue(SingleLiveEvent(UserEnum.SUCCESS))
                }
                _dataFillStatus.postValue(SingleLiveEvent(UserEnum.SUCCESS))
            }
        }else{
            _dataFillStatus.postValue(SingleLiveEvent(UserEnum.FAILURE))
        }
    }

    fun fetchAllSubjects() = addSubjectDao.getListFromBB(prefManager.getString(
        UtilConstants.USER_PHONE_NUMBER,""))

    private val _dataUpdateStatus = MutableLiveData<SingleLiveEvent<UserEnum>>()
    val dataUpdateStatus: LiveData<SingleLiveEvent<UserEnum>>
        get() = _dataUpdateStatus

    fun editExistingMarks(score: Int, name: String, subject: String){
        if (score != null){
            appExecutors.diskIO().execute{
                addSubjectDao.updateMarks(score, name, subject)
                _dataUpdateStatus.postValue(SingleLiveEvent(UserEnum.SUCCESS))
            }
        }else{
            _dataUpdateStatus.postValue(SingleLiveEvent(UserEnum.FAILURE))
        }
    }
}