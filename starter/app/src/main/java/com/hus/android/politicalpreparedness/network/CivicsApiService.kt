package com.hus.android.politicalpreparedness.network

import com.hus.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.hus.android.politicalpreparedness.network.models.ElectionResponse
import com.hus.android.politicalpreparedness.network.models.RepresentativeResponse
import com.hus.android.politicalpreparedness.network.models.VoterInfoResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

// TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project)
private val moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(ElectionAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(CivicsHttpClient.getClient())
        .baseUrl(BASE_URL)
        .build()

/**
 *  Documentation for the Google Civics API Service can be found at https://developers.google.com/civic-information/docs/v2
 */

interface CivicsApiService {
    @GET("representatives")
    fun gettingRepresentativeInformations(
            @Query("address") address: String): Deferred<RepresentativeResponse>
    @GET("voterinfo")
    fun gettingVoterinformation(
            @Query("address") address: String,
            @Query("electionId") electionId: Int): Deferred<VoterInfoResponse>
    @GET("elections")
    fun gettingElection(): Deferred<ElectionResponse>




    //TODO: Add elections API Call

    //TODO: Add voterinfo API Call

    //TODO: Add representatives API Call
}

object CivicsApi {

    val retrofitService: CivicsApiService by lazy {
        retrofit.create(CivicsApiService::class.java)
    }

}