package com.anil.tailwebstask.addSubject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anil.tailwebstask.addSubject.repo.AddSubjectRepository
import com.anil.tailwebstask.signInPage.entityModel.Marks

class AddSubjectViewModel internal constructor(
    private var addSubjectRepository: AddSubjectRepository): ViewModel(){

    override fun onCleared() {
        super.onCleared()
    }

    fun addASubject(marks: Marks) = addSubjectRepository.insertUser(marks)

    var getList: LiveData<List<Marks>> = addSubjectRepository.fetchAllSubjects()

    fun fetchDataFillStatus() = addSubjectRepository.dataFillStatus
}