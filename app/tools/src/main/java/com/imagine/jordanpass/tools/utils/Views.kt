package com.imagine.jordanpass.tools.utils

import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.activity.OnBackPressedDispatcher
import androidx.navigation.NavController
import com.imagine.jordanpass.tools.ext.logd


object Views {

    /**
     * Hides the passed view on the destination supplied by adding OnDestinationChangedListener to navController
     * @param fragmentIdList List of fragment id that the view will be hidden on
     * @param navHostFragment NavHostFragment of your app
     * @param view The view that will be hidden on each supplied fragment
     * */
    fun hideViewOnDestinationChange(fragmentIdList : List<Int>, navController: NavController, listView: List<View>){

        navController.addOnDestinationChangedListener{_,destination,_ ->
            if(fragmentIdList.contains(destination.id)){
//                view.visibility = View.GONE
                listView.forEach {
                    it.visibility = View.GONE
                }
            }else{
//                view.visibility = View.VISIBLE
                listView.forEach {
                    it.visibility = View.VISIBLE
                }
            }

        }
    }


    /**
     * Shows the passed view on the destination supplied by adding OnDestinationChangedListener to navController
     * @param fragmentIdList List of fragment id that the view will be hidden on
     * @param navHostFragment NavHostFragment of your app
     * @param view The view that will be hidden on each supplied fragment
     * */
    fun showViewOnDestinationChange(fragmentIdList : List<Int>, navController: NavController, listView: List<View>){

        navController.addOnDestinationChangedListener{_,destination,_ ->
            if(fragmentIdList.contains(destination.id)){
//                view.visibility = View.GONE
                listView.forEach {
                    it.visibility = View.VISIBLE
                }
            }else{
//                view.visibility = View.VISIBLE
                listView.forEach {
                    it.visibility = View.GONE
                }
            }

        }
    }

    /**
     * Hides the status bar and navigation bar
     * @param hide True: hide systemUI, False: show systemUI
     * @param activity The app activity
     * */
    fun showOrHideSystemUi(hide:Boolean, activity: Activity){
        if(hide){
            activity.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        else{
            activity.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }



    /**
     * Get Screen Width
     * @param activity The Activity wants to get width for it
     * * @return Screen width pixel
     * */
    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }

    }


    /**
     * Get Item Width for Dynamic width
     * @param screenWidth The Width of screen have Items in it
     * @param numberOfItem The Number of Items want to add in screen
     * @param marginAroundItems The margin around container have items
     * @param marginBetweenItem The margin between items together
     * @return Item width pixel
     * */
    fun getItemWidth(
        screenWidth: Int,
        numberOfItem: Int,
        marginAroundItems: Int,
        marginBetweenItem: Int
    ): Int {
        val viewItems = screenWidth - (marginAroundItems * 2)
        val withoutItemMargin = viewItems - ((numberOfItem - 1) * marginBetweenItem)

        return withoutItemMargin / numberOfItem
    }


//    fun hideBottomNavigation(splash : Int, navHostFragment: NavHostFragment, view: BottomNavigationView){
//        navHostFragment.navController.addOnDestinationChangedListener{_,destination,_ ->
//
//            when(destination.id){
//                splash -> {
//                    view.visibility = View.GONE
//                }
//                else -> {
//                    view.visibility = View.VISIBLE
//                }
//            }
//
//        }
//    }

}