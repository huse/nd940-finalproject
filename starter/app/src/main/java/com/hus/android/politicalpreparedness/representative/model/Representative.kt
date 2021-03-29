package com.hus.android.politicalpreparedness.representative.model

import com.hus.android.politicalpreparedness.network.models.Office
import com.hus.android.politicalpreparedness.network.models.Official

data class Representative (
        val official: Official,
        val office: Office
)