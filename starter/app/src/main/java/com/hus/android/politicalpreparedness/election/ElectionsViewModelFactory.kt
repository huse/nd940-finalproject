package com.hus.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hus.android.politicalpreparedness.database.ElectionDao

//TODO: Create Factory to generate ElectionViewModel with provided election datasource
class ElectionsViewModelFactory(private val electionDao: ElectionDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <viewModel : ViewModel?> create(modelClass: Class<viewModel>): viewModel {
        if (modelClass.isAssignableFrom(ElectionsViewModel::class.java)) {
            return ElectionsViewModel(electionDao) as viewModel
        }
        throw IllegalArgumentException("viewModel error")
    }
}