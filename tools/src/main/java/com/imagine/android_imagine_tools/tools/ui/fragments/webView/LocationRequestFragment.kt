package com.imagine.android_imagine_tools.tools.ui.fragments.webView

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.imagine.android_imagine_tools.tools.R
import com.imagine.android_imagine_tools.tools.ext.logd

/**
 * Requests permission and retrieves location in LatLng format
 * To use this , call LocationRequestFragment.startLocationRetrieve(supportFragmentManager)
 * @param fragment the current fragment
 * @param locationCoordinatesCallback listener that returns a Pair of result (LatLng) and a boolean the indicated whether the process was successful
 * Pair first - LatLng
 * Pair second - success Boolean
 * */
class LocationRequestFragment() : Fragment() {

    private var askForGps = true
    private var alertDialog: AlertDialog? = null

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationCallback: LocationCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        checkLocationPermission()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ProgressBar(inflater.context)
    }

    private val locationsPermissionLauncher = this.registerForActivityResult<String, Boolean>(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            try {
                try {
                    checkLocationPermission()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    locationCoordinatesCallback?.invoke(Pair(null, false))
                    removeFragment()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                locationCoordinatesCallback?.invoke(Pair(null, false))
                removeFragment()
            }
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(
                        this.requireContext(),
                        R.string.location_permission_warning,
                        Toast.LENGTH_SHORT
                    ).show()
                    locationCoordinatesCallback?.invoke(Pair(null, false))
                    removeFragment()
                } else {
                    showSettingsPopUpForLocation()
                    locationCoordinatesCallback?.invoke(Pair(null, false))
                    removeFragment()
                }
            }
        }
    }


    private fun checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showPopUp(requireActivity())
                    locationCoordinatesCallback?.invoke(Pair(null, false))
                } else {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    locationsPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            } else {
                if (askForGps) {
                    displayLocationSettingsRequest()
                }
            }
        } else {
            if (askForGps) {
                displayLocationSettingsRequest()
            }
        }
    }


    private fun showPopUp(activity: Activity) {
        AlertDialog.Builder(this.requireContext())
            .setTitle(R.string.location_permission_popup_title)
            .setMessage(R.string.location_permission_rationale)
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which -> //                        ActivityCompat.requestPermissions(NearbyMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    locationsPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(
                        activity,
                        R.string.location_permission_warning,
                        Toast.LENGTH_SHORT
                    ).show()
                    removeFragment()
                })
            .show()
    }


    private fun showPopUpGps(show: Boolean) {

        if (alertDialog == null) {
            alertDialog = AlertDialog.Builder(this.requireContext())
                .setTitle(R.string.location_permission_popup_title)
                .setMessage(R.string.location_permission_rationale)
                .setPositiveButton(android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, which -> //                        ActivityCompat.requestPermissions(NearbyMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                    locationsPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        askForGps = true
                        checkLocationPermission()
                    })
                .setNegativeButton(android.R.string.cancel,
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(
                            this.requireContext(),
                            R.string.location_permission_warning,
                            Toast.LENGTH_SHORT
                        ).show()
                    }).create()
        }

        if (show) {
            alertDialog?.show()
        } else {
            alertDialog?.hide()
        }


    }


    private fun showSettingsPopUpForLocation() {
        AlertDialog.Builder(this.requireContext())
            .setTitle(R.string.location_permission_popup_title)
            .setMessage(R.string.location_permission_settings)
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }


    private val activityResultLauncher = this.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.logd("result")
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val data: Intent? = result.data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            checkLocationPermission()
        }
    }


    private val resolutionResultContract =
        this.registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Resolution was successful, handle it accordingly
                // You can access the result using result.data
                askForGps = false
                getLocation()
//                Toast.makeText(requireContext(), "GPS Allow", Toast.LENGTH_SHORT).show()
            } else {
                locationCoordinatesCallback?.invoke(Pair(null, false))
                removeFragment()
//                Toast.makeText(requireContext(), "GPS Deny", Toast.LENGTH_SHORT).show()
//            binding.mapRefreshBtn.clearAnimation()
//                showPopUpGps(true)
                askForGps = true
                // Resolution failed or was canceled, handle it accordingly
            }
        }


    private fun displayLocationSettingsRequest() {
        val googleApiClient = GoogleApiClient.Builder(this.requireContext())
            .addApi(LocationServices.API)
            .build()
        googleApiClient.connect()

        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {

                setMinUpdateDistanceMeters(100f)
                setGranularity(Granularity.GRANULARITY_FINE)
                setWaitForAccurateLocation(false)


//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            interval = 10000
//            fastestInterval = 10000 / 2
            }

        val builder =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest.build())
                .apply {
                    setAlwaysShow(true)
                }

        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
//                    Toast.makeText(requireContext(), "GPS SUCCESS", Toast.LENGTH_SHORT).show()
                    askForGps = false
                    getLocation()

                }

                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    try {
//                        Toast.makeText(requireContext(), "GPS REQUIRED", Toast.LENGTH_SHORT).show()

                        resolutionResultContract.launch(
                            IntentSenderRequest.Builder(status.resolution?.intentSender!!)
                                .build()
                        )


                    } catch (ignored: IntentSender.SendIntentException) {
                        locationCoordinatesCallback?.invoke(Pair(null, false))
                        removeFragment()
                    }
                }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    locationCoordinatesCallback?.invoke(Pair(null, false))
                    removeFragment()
                    // Handle SETTINGS_CHANGE_UNAVAILABLE case
//                    Toast.makeText(requireContext(), "GPS Disable", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {

// Inside your function or onCreate method:
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireContext())
        mLocationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.forEach { location ->
                    location?.let {
                        locationCoordinatesCallback?.invoke(
                            Pair(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                ), true
                            )
                        )
                        removeFragment()
                    }
                }
            }

        }

        val mLocationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 31536000000L).apply {

            }

        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest.build(),
            mLocationCallback,
            Looper.myLooper()
        )


    }

    private fun removeFragment() {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }


    companion object {
        private val FRAGMENT_TAG = "location_request_fragment"
        private var locationCoordinatesCallback: ((Pair<LatLng?, Boolean>) -> Unit)? = null


        fun setupLocationCallback(callback: (Pair<LatLng?, Boolean>) -> Unit) {
            locationCoordinatesCallback = callback
        }

        /**
         *Call this to start location retrieve
         */
        fun startLocationRetrieve(supportFragmentManager: FragmentManager) {

            supportFragmentManager.apply {
                beginTransaction().add(
                    LocationRequestFragment(),
                    FRAGMENT_TAG
                ).commit()
            }
        }


    }

}