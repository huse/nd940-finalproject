package com.hus.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hus.android.politicalpreparedness.R
import com.hus.android.politicalpreparedness.database.ElectionDatabase
import com.hus.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.hus.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.hus.android.politicalpreparedness.election.adapter.ElectionClickListener

class ElectionsFragment: Fragment() {

    //TODO: Declare ViewModel
    private lateinit var electionsViewModel: ElectionsViewModel
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        val binding: FragmentElectionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_election, container, false)
        val electionApp = requireNotNull(this.activity).application
        val resourceOfData = ElectionDatabase.getInstance(electionApp).electionDao
        val electionsViewModelFactory = ElectionsViewModelFactory(resourceOfData)
        electionsViewModel = ViewModelProvider(this, electionsViewModelFactory).get(ElectionsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.electionsviewmodel = electionsViewModel
        val storeElectionAdapter = ElectionListAdapter(ElectionClickListener {
            findNavController()
                    .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
        })
        electionsViewModel.savedElections.observe(viewLifecycleOwner, Observer {
            storeElectionAdapter.submitList(it)
        })
        binding.storedElectionRecyclerView.adapter = storeElectionAdapter
        val futureElectionAdapter = ElectionListAdapter(ElectionClickListener {
            findNavController()
                    .navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
        })
        binding.futureElectionRecyclerView.adapter = futureElectionAdapter

        electionsViewModel.futureElection.observe(viewLifecycleOwner, Observer {
            it?.let {
                futureElectionAdapter.submitList(it)
            }
        })
        return binding.root
    }


    //TODO: Refresh adapters when fragment loads

}