package com.imagine.android_imagine_tools.tools.ui.dialogs.imageSelectionMethod

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt

object ImageSelectionMethodConstants {

    internal lateinit var fileProviderAuthority: String
    internal lateinit var applicationID: String

    fun init(context: Context?) {
        fileProviderAuthority = "${context?.packageName}.com.imagine.tools.file.provider"
        applicationID = context?.packageName ?: ""
    }

    // -colors-
    @ColorInt var backGroundColor: Int = Color.parseColor("#ffffff")
    @ColorInt var cameraButtonTextColor: Int = Color.parseColor("#000000")
    @ColorInt var galleryButtonTextColor: Int = Color.parseColor("#000000")
    @ColorInt var galleryButtonBackgroundColor: Int = Color.parseColor("#F0EDE8")
    @ColorInt var cameraButtonBackgroundColor: Int = Color.parseColor("#F0EDE8")
    @ColorInt var cameraIconColor: Int = Color.parseColor("#9a4a3a")
    @ColorInt var galleryIconColor: Int = Color.parseColor("#9a4a3a")

    // -strings-
    var cameraString = "Camera"
    var galleryString = "Gallery"
    var permissionDeniedDialogMessage =
        "Permission was denied for this action, you can grant permission to use this action from the settings page. \nopen settings page?"
    var permissionDeniedDialogTitle = "Permission denied previously"

}