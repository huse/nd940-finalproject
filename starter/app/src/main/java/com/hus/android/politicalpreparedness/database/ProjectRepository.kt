package com.hus.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hus.android.politicalpreparedness.network.CivicsApi
import com.hus.android.politicalpreparedness.network.models.Election
import com.hus.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository (private val electionDao: ElectionDao) {

    val voterInfoMutableLiveData = MutableLiveData<VoterInfoResponse>()
    val storedElectionsLiveDataList: LiveData<List<Election>> = electionDao.gettingStoredElections()
    val electionLiveDataList: LiveData<List<Election>> = electionDao.gettingElections()

    suspend fun updatingDataElectionInRepo(election: Election) {
        withContext(Dispatchers.IO) {
            electionDao.updatingTheElection(election)
        }
    }

    suspend fun refreshingResultOfElectionsInRepo() {
        try {
            withContext(Dispatchers.IO) {
                val networkResult = CivicsApi.retrofitService.gettingElection().await()
                electionDao.pushElections(networkResult.elections)
            }
        } catch (e: Exception) {e.printStackTrace()}
    }
    fun gettingOneElectionByIdFromRepo(id: Int) = electionDao.gettingOneElectionById(id)

    suspend fun gettingVotersInformationFromRepo(voterAddress: String, idElection: Int) {
        try {
            withContext(Dispatchers.IO) {
                val infoNetworkResult = CivicsApi.retrofitService.gettingVoterinformation(voterAddress, idElection).await()
                voterInfoMutableLiveData.value = infoNetworkResult
            }
        } catch (e: Exception) {e.printStackTrace()}
    }

}