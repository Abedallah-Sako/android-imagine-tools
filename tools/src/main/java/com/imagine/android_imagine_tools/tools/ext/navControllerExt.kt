package com.imagine.android_imagine_tools.tools.ext

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController


@MainThread
fun NavController.safeNavigate(directions: NavDirections, currentFragmentID: Int) {
    if (currentFragmentID == currentDestination?.id) {
        this.navigate(directions)
    }
}

fun Fragment.isNavControllerSet(): Boolean {
    return try {
        this.findNavController()
        true // NavController is set
    } catch (e: IllegalStateException) {
        false // NavController is not set
    }
}