package com.imagine.android_imagine_tools.tools.ui.fragments.webView

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class WriteExternalPermissionRequestFragment : Fragment() {
    private val permissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        permissionRequestCallback?.invoke(it)
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        checkPermission()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ProgressBar(inflater.context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkPermission() {
        //handle permission on api < 29
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            requestPermission()
        } else {
            permissionRequestCallback?.invoke(true)
        }
    }


    private fun requestPermission() {
        //check if permission is granted
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //permission is granted
            permissionRequestCallback?.invoke(true)
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {  // if permission was denied before


            showSettingsCallback?.invoke()
            permissionRequestCallback?.invoke(false)
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()


        } else { //request permission
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }


    companion object {
        private val FRAGMENT_TAG = "write_external_permission_request_fragment"
        private var permissionRequestCallback: ((Boolean) -> Unit)? = null
        private var showSettingsCallback: (() -> Unit)? = null


        fun setupPermissionRequestCallback(callback: (Boolean) -> Unit) {
            permissionRequestCallback = callback
        }

        /**
         * when this is called, the developer is expected to show a popup and ask the user
         * to open the settings page in order to allow storage access.
         */
        fun setupShowSettingsCallback(callback: () -> Unit) {
            showSettingsCallback = callback
        }

        /**
         *Call this to start storage access permission request
         */
        fun startPermissionRequest(supportFragmentManager: FragmentManager) {

            supportFragmentManager.apply {
                beginTransaction().add(
                    WriteExternalPermissionRequestFragment(),
                    FRAGMENT_TAG
                ).commit()
            }
        }


    }
}

