package com.hus.android.politicalpreparedness.representative

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hus.android.politicalpreparedness.network.CivicsApi
import com.hus.android.politicalpreparedness.network.models.Address
import com.hus.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel: ViewModel() {

    val _representatives = MutableLiveData<List<Representative>>()
    val address = MutableLiveData<Address>()
    val TAG = "RepresentativeViewModel"
    fun getRepresentatives() {
        if (address.value != null) {
            viewModelScope.launch {
                try {
                    val (offices, officials) = CivicsApi.retrofitService.gettingRepresentativeInformations(address.value!!.toFormattedString()).await()
                    Log.d(TAG, "hhh  getRepresentatives called. offices:   "   + offices)
                    Log.d(TAG, "hhh  getRepresentatives called. officials:   "   + officials)

                    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
                    Log.d(TAG, "hhh  getRepresentatives called. _representatives.value:   "   + _representatives.value)


                } catch (e: Throwable) {
                    Log.d(TAG, "hhh  getRepresentatives called. failed:   ")

                    e.printStackTrace()
                }
            }
        }
    }
    //TODO: Establish live data for representatives and address

    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
