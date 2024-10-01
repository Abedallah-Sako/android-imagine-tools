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

class NotificationRequestFragment : Fragment() {
    private val permissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        notificationPermissionRequestCallback?.invoke(it)
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        checkNotificationPermission()
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

    private fun checkNotificationPermission() {
        //handle notification on api >= 33
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        } else {
            notificationPermissionRequestCallback?.invoke(true)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        //check if permission is granted
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //permission is granted
            notificationPermissionRequestCallback?.invoke(true)
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.POST_NOTIFICATIONS
            )
        ) {  // if permission was denied before


            notificationShowSettingsCallback?.invoke()
            notificationPermissionRequestCallback?.invoke(false)
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()


        } else { //request permission
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


    companion object {
        private val FRAGMENT_TAG = "notification_permission_request_fragment"
        private var notificationPermissionRequestCallback: ((Boolean) -> Unit)? = null
        private var notificationShowSettingsCallback: (() -> Unit)? = null


        fun setupNotificationPermissionRequestCallback(callback: (Boolean) -> Unit) {
            notificationPermissionRequestCallback = callback
        }

        /**
         * when this is called, the developer is expected to show a popup and ask the user
         * to open the settings page in order to allow notifications.
         */
        fun setupShowSettingsCallback(callback: () -> Unit) {
            notificationShowSettingsCallback = callback
        }

        /**
         *Call this to notification permission request
         */
        fun startNotificationPermissionRequest(supportFragmentManager: FragmentManager) {

            supportFragmentManager.apply {
                beginTransaction().add(
                    NotificationRequestFragment(),
                    FRAGMENT_TAG
                ).commit()
            }
        }


    }
}

