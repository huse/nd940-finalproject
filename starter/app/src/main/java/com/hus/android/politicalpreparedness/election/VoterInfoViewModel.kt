package com.hus.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.hus.android.politicalpreparedness.database.ElectionDao
import com.hus.android.politicalpreparedness.database.ProjectRepository
import com.hus.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class VoterInfoViewModel(electionDao: ElectionDao) : ViewModel() {
    private val projectRepository = ProjectRepository(electionDao)
    private var TAG = "VoterInfoViewModel"
    val voterInformationMutable = projectRepository.voterInfoMutableLiveData
    val storedElectionStatusMutable = MutableLiveData<Boolean>()
    var linkUrlMutable = MutableLiveData<String>()
/*    val election: Election
        get() {
           return electionId
        }*/
    //source:  https://developer.android.com/reference/kotlin/androidx/lifecycle/package-summary
    private val idElectionMutable = MutableLiveData<Int>()
/*    val election = idElectionMutable.switchMap {
        liveData {
            emitSource(repo.gettingOneElectionByIdFromRepo(it))
        }
    }*/
    //repo.getElectionById(electionId)/**/
    fun updatingElections(election: Election) {

    Log.d(TAG, "hhh  updatingElections called. election:   "  + election)
    viewModelScope.launch {
        election.Saved = !election.Saved
        storedElectionStatusMutable.value = !storedElectionStatusMutable.value!!
        projectRepository.updatingDataElectionInRepo(election)
    }
}
    fun gettingElectionById(electionId: Int) {
        Log.d(TAG, "hhh  gettingVotersInformations called. electionId:    "  + electionId)
        idElectionMutable.value = electionId
    }
    init { storedElectionStatusMutable.value = false}
    fun gettingVotersInformations(voterAddress: String, electionId: Int) {
        Log.d(TAG, "hhh  gettingVotersInformations called. voterAddress:     "  + voterAddress)

        viewModelScope.launch { projectRepository.gettingVotersInformationFromRepo(voterAddress, electionId) }
    }
    fun gettingUrl(url: String) {
        Log.d(TAG, "hhh  gettingVotersInformations called. url:     "  + url)
        this.linkUrlMutable.value = url }

    fun settingElectionById(electionId: Int) {
        Log.d(TAG, "hhh  settingElectionById called. electionId:    "  + electionId)
        idElectionMutable.value = electionId
    }
    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}