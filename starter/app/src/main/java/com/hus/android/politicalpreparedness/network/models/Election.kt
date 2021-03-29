package com.hus.android.politicalpreparedness.network.models


import com.squareup.moshi.*
import androidx.room.*
import java.util.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "election_table")
@Parcelize
data class Election(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "name")val name: String,
        @ColumnInfo(name = "electionDay")val electionDay: Date,
        @Embedded(prefix = "division_") @Json(name="ocdDivisionId") val division: Division,
        @ColumnInfo(name = "Saved") var Saved: Boolean = false
) : Parcelable