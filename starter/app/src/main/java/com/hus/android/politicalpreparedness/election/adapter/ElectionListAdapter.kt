package com.hus.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hus.android.politicalpreparedness.databinding.ElectionRowListBinding
import com.hus.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val electionClickListener: ElectionClickListener): ListAdapter<Election, MyViewHolder>(CallingElection) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ElectionRowListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val electionItemInRow = getItem(position)
        holder.itemView.setOnClickListener {
            electionClickListener.onClick(electionItemInRow)
        }
        holder.bind(electionItemInRow)
    }
}



class ElectionClickListener(val clickListener: (Election) -> Unit) {
    fun onClick(election: Election) = clickListener(election)
}
class MyViewHolder(private val binding: ElectionRowListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(election: Election) {
        binding.electionclass = election
        binding.executePendingBindings()
    }
}