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

    var getList: LiveData<List<Marks>> = addSubjectRepository.fetchAllSubjects()

    fun fetchDataFillStatus() = addSubjectRepository.dataFillStatus

    fun fetchMatchedRow(name: String, subject: String, number: String, score: Int) =
        addSubjectRepository.insertSubject(name, subject, number, score)

    fun fetchUpdateRow(score: Int, name: String, subject: String) =
        addSubjectRepository.editExistingMarks(score, name, subject)

    fun fetchUpdateStatus() = addSubjectRepository.dataUpdateStatus
}