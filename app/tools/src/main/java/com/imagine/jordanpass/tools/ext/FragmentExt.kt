package com.imagine.jordanpass.tools.ext

import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.imagine.jordanpass.tools.model.landMark.LandMarkModel

fun Fragment.changeStatusBarColor(@ColorRes color: Int) {
    val window: Window = requireActivity().window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    val decorView = window.decorView
    val wic = WindowInsetsControllerCompat(window, decorView)
    wic.isAppearanceLightStatusBars = true // true or false as desired.

    window.statusBarColor = ContextCompat.getColor(requireContext(), color)
}
