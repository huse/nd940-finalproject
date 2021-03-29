package com.hus.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hus.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query

    //TODO: Add select all election query

    //TODO: Add select single election query

    //TODO: Add delete query

    //TODO: Add clear query
    @Update
    fun updatingTheElection(election: Election)

    @Query("DELETE FROM election_table where id=:id")
    fun deletingOneElectionById(id: Int)




    @Query("SELECT * FROM election_table where Saved = 1")
    fun gettingStoredElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id=:id")
    fun gettingOneElectionById(id: Int): LiveData<Election>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun pushElections(elections: List<Election>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun pushElection(election: Election)




    @Query("SELECT * FROM election_table")
    fun gettingElections(): LiveData<List<Election>>



}