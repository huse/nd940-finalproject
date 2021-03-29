package com.hus.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.hus.android.politicalpreparedness.R
import com.hus.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.hus.android.politicalpreparedness.network.models.Address
import com.hus.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import java.util.Locale

class DetailFragment : Fragment() , AdapterView.OnItemSelectedListener{

    companion object {

        private const val ID_APP = "com.example.android.politicalpreparedness"
        private const val CODE_LOCATION_REQUEST = 10
        private const val TAG = "DetailFragment"
    }

    //TODO: Declare ViewModel
    private lateinit var address1: Address
    private lateinit var stateChosen: String
    private lateinit var fragmentRepresentativeBinding: FragmentRepresentativeBinding
    private lateinit var representativeViewModel: RepresentativeViewModel
    private lateinit var representativeListAdapter: RepresentativeListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentRepresentativeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_representative, container, false)
        fragmentRepresentativeBinding.lifecycleOwner = viewLifecycleOwner
        representativeViewModel = ViewModelProvider(this).get(RepresentativeViewModel::class.java)
        fragmentRepresentativeBinding.viewModel = representativeViewModel
        fragmentRepresentativeBinding.state.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.states))
        fragmentRepresentativeBinding.state.onItemSelectedListener = this
        fragmentRepresentativeBinding.buttonLocation.setOnClickListener {checkingPermissions() }
        fragmentRepresentativeBinding.buttonSearch.setOnClickListener { address1 = Address(fragmentRepresentativeBinding.addressLine1.text.toString(), fragmentRepresentativeBinding.addressLine2.text.toString(), fragmentRepresentativeBinding.city.text.toString(),
                    stateChosen, fragmentRepresentativeBinding.zip.text.toString())
            representativeViewModel.address.value = address1
            representativeViewModel.getRepresentatives()
            hideKeyboard()
        }
        representativeListAdapter = RepresentativeListAdapter()
        fragmentRepresentativeBinding.representativesRecyclerView.adapter = representativeListAdapter
        representativeViewModel._representatives.observe(viewLifecycleOwner, Observer { representativeListAdapter.submitList(it) })

        return fragmentRepresentativeBinding.root
        //TODO: Establish bindings

        //TODO: Define and assign Representative adapter

        //TODO: Populate Representative adapter

        //TODO: Establish button listeners for field and location search

    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        stateChosen = parent?.getItemAtPosition(position) as String
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        stateChosen = "Washington"
    }
    private fun checkingPermissions(): Boolean {
        Log.d ( TAG, "hhh calling    checkingPermissions"  )

        return if (isPermissionGranted()) { getLocation()
            true
        } else {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(
                    permissions,
                    CODE_LOCATION_REQUEST)
            //TODO: Request Location permissions
            false
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty() || (requestCode == CODE_LOCATION_REQUEST && grantResults[0] == PackageManager.PERMISSION_DENIED)) {
            Snackbar.make(fragmentRepresentativeBinding.motionSceneLayoutId, "permission not granted", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Settings") {
                        startActivity(Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = Uri.fromParts("package", ID_APP, null)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }.show()
        } else {
            checkingPermissions()
        }
        //TODO: Handle location permission result to get location on permission granted
    }

    private fun isPermissionGranted() : Boolean {

        Log.d ( TAG, "hhh calling    isPermissionGranted"  )
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION))
        //TODO: Check if permission is already granted and return (true = granted, false = denied/other)
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        Log.d ( TAG, "hhh calling    getLocation"  )
        LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener {
                    if (it != null) {
                        representativeViewModel.address.value = geoCodeLocation(it)
                        fragmentRepresentativeBinding.state.setSelection(resources.getStringArray(R.array.states).indexOf(geoCodeLocation(it).state))
                        representativeViewModel.getRepresentatives()
                    } else
                        Log.d ( TAG, "hhh   getLocation :  null"  )
                }
                .addOnFailureListener { it.printStackTrace()}
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

}