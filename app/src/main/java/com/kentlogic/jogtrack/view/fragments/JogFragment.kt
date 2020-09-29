package com.kentlogic.jogtrack.view.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kentlogic.jogtrack.R
import com.kentlogic.jogtrack.adapters.JogAdapter
import com.kentlogic.jogtrack.util.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.kentlogic.jogtrack.util.SortType
import com.kentlogic.jogtrack.util.TrackingUtility
import com.kentlogic.jogtrack.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_jog.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class JogFragment: Fragment(R.layout.fragment_jog), EasyPermissions.PermissionCallbacks {

    private lateinit var  jogAdapter: JogAdapter

    //dagger manages the vm and will assign the correct vm
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        setupRecyclerView()

        //Update the dropdown selected
        when(viewModel.sortType) {
            SortType.DATE -> spFilter.setSelection(0)
            SortType.RUNNING_TIME -> spFilter.setSelection(1)
            SortType.DISTANCE -> spFilter.setSelection(2)
            SortType.AVG_SPEED -> spFilter.setSelection(3)
            SortType.CALORIES_BURNED -> spFilter.setSelection(4)
        }

        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> viewModel.sortJogs(SortType.DATE)
                    1 -> viewModel.sortJogs(SortType.RUNNING_TIME)
                    2 -> viewModel.sortJogs(SortType.DISTANCE)
                    3 -> viewModel.sortJogs(SortType.AVG_SPEED)
                    4 -> viewModel.sortJogs(SortType.CALORIES_BURNED)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        viewModel.jogs.observe(viewLifecycleOwner, Observer {jogs ->
            jogs?.let {
                jogAdapter.submitList(jogs)
                ll_empty_list.visibility = if(jogs.isEmpty()) View.VISIBLE else View.GONE
            }
        })

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_jogFragment_to_trackingFragment)
        }
    }

    private fun setupRecyclerView() = rvRuns.apply {
        jogAdapter = JogAdapter()
        adapter = jogAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestPermission() {
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "Location permissions are required for the app the work properly.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Location permissions are required for the app the work properly.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        //Show settings dialog if user denies perms permanently
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermission()
        }
        requestPermission()
    }

    //check result when we request permissions and direct them to easypermissions
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //this - this fragment will receive
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}