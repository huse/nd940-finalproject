package com.hus.android.politicalpreparedness.election.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hus.android.politicalpreparedness.network.models.Election

object CallingElection: DiffUtil.ItemCallback<Election>() {


    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem === newItem
    }
}
