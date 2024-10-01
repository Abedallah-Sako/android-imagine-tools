package com.imagine.android_imagine_tools.tools.utils

import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imagine.android_imagine_tools.tools.ext.fadeInToVisible
import com.imagine.android_imagine_tools.tools.ext.fadeOutToGone
import java.lang.Exception
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule


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
                listView.forEach {
                    it.fadeOutToGone()
                }
            }else{
                listView.forEach {
                    it.fadeInToVisible()
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


    /**
     * Creates a timer task to automate scroll every 5 seconds
     * This method returns a TimerTask which should be stopped based on the lifecycle of the activity
     * Please note this method is only used for linearLayoutManager currently
     *
     * @param recyclerView The recycler view want to automate scroll
     * */
    fun automateRecyclerViewScroll(recyclerView: RecyclerView, lifecycleOwner: LifecycleOwner, delay:Long =0, period:Long = 5000): TimerTask {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val adapter = recyclerView.adapter as RecyclerView.Adapter

        val task =  Timer().schedule(delay,period) {

            try {
                val currentPosition = layoutManager.findFirstVisibleItemPosition()

                if (currentPosition + 1 >= adapter.itemCount) {
                    recyclerView.smoothScrollToPosition(0)
                } else {
                    recyclerView.smoothScrollToPosition(currentPosition + 1)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }

        }
        task.run()

        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                task.cancel()
            }
        })


        return task
    }

}