package com.imagine.jordanpass.tools.ext

import androidx.annotation.MainThread
import androidx.navigation.NavController
import androidx.navigation.NavDirections


@MainThread
fun NavController.safeNavigate(directions: NavDirections, currentFragmentID: Int) {
    if (currentFragmentID == currentDestination?.id) {
        this.navigate(directions)
    }
}