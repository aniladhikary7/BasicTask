package com.anil.tailwebstask.addSubject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anil.tailwebstask.addSubject.repo.AddSubjectRepository

class AddSubjectViewModelFactory (private val addSubjectRepository: AddSubjectRepository):
ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        AddSubjectViewModel(addSubjectRepository) as T

}