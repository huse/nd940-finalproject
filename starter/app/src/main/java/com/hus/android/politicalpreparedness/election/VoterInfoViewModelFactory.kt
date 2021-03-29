package com.hus.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hus.android.politicalpreparedness.database.ElectionDao

//TODO: Create Factory to generate VoterInfoViewModel with provided election datasource
class VoterInfoViewModelFactory(
        private val electionDao: ElectionDao
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <viewModel : ViewModel?> create(modelClass: Class<viewModel>): viewModel {
        if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
            return VoterInfoViewModel(electionDao) as viewModel
        }
        throw IllegalArgumentException("ViewModel error from VoterInfoViewModelFactory")
    }
}