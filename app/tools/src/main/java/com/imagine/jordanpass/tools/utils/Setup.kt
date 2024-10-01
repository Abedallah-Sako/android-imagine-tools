package com.imagine.jordanpass.tools.utils

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imagine.jordanpass.tools.R
import com.imagine.jordanpass.tools.ext.logd


object Setup {

     fun setupBottomNavigation(bottomNavigationView: BottomNavigationView,navHostFragment:NavHostFragment){
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        bottomNavigationView.setOnItemSelectedListener {

            if (!navHostFragment.findNavController().popBackStack(it.itemId, false)) {
                val navOptions = NavOptions
                    .Builder()
                    .setEnterAnim(R.anim.fade_in)
                    .setExitAnim(R.anim.fade_out)
                    .setPopEnterAnim(R.anim.fade_in)
                    .setPopExitAnim(R.anim.fade_out)
                    .build()

                navHostFragment.findNavController().navigate(it.itemId, null, navOptions)
            }
            true
        }
    }
}