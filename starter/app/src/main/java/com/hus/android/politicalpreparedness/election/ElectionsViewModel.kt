package com.hus.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hus.android.politicalpreparedness.database.ElectionDao
import com.hus.android.politicalpreparedness.database.ProjectRepository
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(electionDao: ElectionDao): ViewModel() {

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

    private val projectRepository = ProjectRepository(electionDao)
    val savedElections = projectRepository.storedElectionsLiveDataList
    val futureElection = projectRepository.electionLiveDataList
    init {viewModelScope.launch {projectRepository.refreshingResultOfElectionsInRepo()}}
}