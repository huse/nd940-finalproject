package com.hus.android.politicalpreparedness.database

import android.util.Log
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
    val TAG = "ProjectRepository"
    suspend fun updatingDataElectionInRepo(election: Election) {
        Log.d(TAG, "hhh  updatingDataElectionInRepo called. election:    $election")

        withContext(Dispatchers.IO) {
            electionDao.updatingTheElection(election)
        }
    }

    suspend fun refreshingResultOfElectionsInRepo() {
        try {
            withContext(Dispatchers.IO) {
                val networkResult = CivicsApi.retrofitService.gettingElection().await()
                electionDao.pushElections(networkResult.elections)
                Log.d(TAG, "hhh  refreshingResultOfElectionsInRepo called. networkResult:    $networkResult")

            }
        } catch (e: Exception) {e.printStackTrace()}
    }
    fun gettingOneElectionByIdFromRepo(id: Int) : LiveData<Election> {
        Log.d(TAG, "hhh  gettingOneElectionByIdFromRepo called. id:    $id")
        val result = electionDao.gettingOneElectionById(id)
        Log.d(TAG, "hhh  gettingOneElectionByIdFromRepo called. result:    $result")
        Log.d(TAG, "hhh  gettingOneElectionByIdFromRepo called. result.value:    ${result.value}")


        return result }

    suspend fun gettingVotersInformationFromRepo(voterAddress: String, idElection: Int) {
        Log.d(TAG, "hhh  gettingVotersInformationFromRepo called. voterAddress:    $voterAddress")

        try {
            withContext(Dispatchers.IO) {
                val infoNetworkResult = CivicsApi.retrofitService.gettingVoterinformation(voterAddress, idElection).await()
                voterInfoMutableLiveData.value = infoNetworkResult
                Log.d(TAG, "hhh  gettingVotersInformationFromRepo called. infoNetworkResult:    $infoNetworkResult")
            }
        } catch (e: Exception) {e.printStackTrace()}
    }

}