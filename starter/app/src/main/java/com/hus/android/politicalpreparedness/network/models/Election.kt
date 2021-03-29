package com.hus.android.politicalpreparedness.network.models


import com.squareup.moshi.*
import androidx.room.*
import java.util.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "election_table")
@Parcelize
data class Election(
        @PrimaryKey var id: Int,
        @ColumnInfo(name = "name")var name: String,
        @ColumnInfo(name = "electionDay")var electionDay: Date,
        @Embedded(prefix = "division_") @Json(name="ocdDivisionId") var division: Division,
        @ColumnInfo(name = "Saved") var Saved: Boolean = false
) : Parcelable