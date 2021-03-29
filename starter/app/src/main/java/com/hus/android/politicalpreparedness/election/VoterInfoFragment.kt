package com.hus.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hus.android.politicalpreparedness.R
import com.hus.android.politicalpreparedness.database.ElectionDatabase
import com.hus.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {
    private lateinit var voterInfoViewModel: VoterInfoViewModel
    private var TAG = "VoterInfoFragment"
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "hhh  onCreateView started.")
        val binding: FragmentVoterInfoBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_voter_info, container, false)
        val electionApp = requireNotNull(this.activity).application
        val resourceOfData = ElectionDatabase.getInstance(electionApp).electionDao
        val voterInfoViewModelFactory = VoterInfoViewModelFactory(resourceOfData)
        voterInfoViewModel =
                ViewModelProvider(
                        this, voterInfoViewModelFactory).get(VoterInfoViewModel::class.java)
        binding.lifecycleOwner = this
        binding.voterinfoviewModellayout = voterInfoViewModel
        val electionObject = VoterInfoFragmentArgs.fromBundle(requireArguments()).argElection
        voterInfoViewModel.gettingElectionById(electionObject.id)
        voterInfoViewModel.gettingVotersInformations(electionObject.division.id, electionObject.id)
        binding.saveElectionBt.setOnClickListener {
            voterInfoViewModel.updatingElections(electionObject)
        }
        voterInfoViewModel.storedElectionStatusMutable.observe(viewLifecycleOwner, Observer {
            if (it == true)
                binding.saveElectionBt.text = "UN FOLLOW REPRESENTATIVE"
            else
                binding.saveElectionBt.text = "FOLLOW REPRESENTATIVE"
        })
        voterInfoViewModel.linkUrlMutable.observe(viewLifecycleOwner, Observer {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        })
        return binding.root
        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

    }

    //TODO: Create method to load URL intents

}