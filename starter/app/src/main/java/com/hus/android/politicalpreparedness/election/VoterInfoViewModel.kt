package com.hus.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.*

import com.hus.android.politicalpreparedness.database.ElectionDao
import com.hus.android.politicalpreparedness.database.ProjectRepository
import com.hus.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class VoterInfoViewModel(electionDao: ElectionDao) : ViewModel() {
    private val projectRepository = ProjectRepository(electionDao)
    private var TAG = "VoterInfoViewModel"
    val voterInformationMutable = projectRepository.voterInfoMutableLiveData
    val storedElectionStatusMutable = MutableLiveData<Boolean>()
    private val idElectionMutable = MutableLiveData<Int>()
    var linkUrlMutable = MutableLiveData<String>()

/*    val election: Election
        get() {
           return electionId
        }*/
    //source:  https://developer.android.com/reference/kotlin/androidx/lifecycle/package-summary
/*    private val idElectionMutable = LiveData<Int>()
    val election = idElectionMutable.switchMap {
        liveData {
            emitSource(projectRepository.gettingOneElectionByIdFromRepo(it))
        }
    }*/

/*    private var electionId = Transformations.switchMap()
    var election = electionId.switchMap {
        liveData {
            emitSource(repo.getElectionById(it))
        }
    }*/

     var election1: LiveData<Election> = Transformations.switchMap<Int, Election>( MutableLiveData<Int>()) { id: Int -> projectRepository.gettingOneElectionByIdFromRepo(id)}

    //val election: Election = { id: Int -> projectRepository.gettingOneElectionByIdFromRepo(id)}

    var election = election1.value
        //projectRepository.getElectionById(electionId)/**/
    fun updatingElections(election222: Election) {

    Log.d(TAG, "hhh  updatingElections called. election:   "  + election222)
    viewModelScope.launch {
        election222.Saved = !election222.Saved
        storedElectionStatusMutable.value = !storedElectionStatusMutable.value!!
        projectRepository.updatingDataElectionInRepo(election222)
    }
    }
    fun gettingElectionById(electionId: Int) {
        Log.d(TAG, "hhh  gettingVotersInformations called. electionId:    "  + electionId)
        Log.d(TAG, "hhh  gettingVotersInformations called. election:    "  + election)
        Log.d(TAG, "hhh  gettingVotersInformations called. election1:    "  + election1)
        Log.d(TAG, "hhh  gettingVotersInformations called. gettingOneElectionByIdFromRepo:    "  + projectRepository.gettingOneElectionByIdFromRepo(electionId).value)


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

    fun settingElectionById(election333: Election) {
        Log.d(TAG, "hhh  settingElectionById called. electionId:    "  + election333)
        //election.electionDay = !election333.electionDay
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