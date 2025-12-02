package com.imagine.android_imagine_tools.tools.utils

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class CustomSnapHelper : PagerSnapHelper() {
    //snaps to the start of the view
    private var attachedRecyclerView: RecyclerView?= null

    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
        attachedRecyclerView = recyclerView
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        val smallestDp = attachedRecyclerView?.resources?.configuration?.smallestScreenWidthDp?:0

        //tablets/foldable
        if(smallestDp >= 600){
            val lm = layoutManager as? LinearLayoutManager
            val firstCompletelyVisibleViewPosition = lm?.findFirstCompletelyVisibleItemPosition()

            if(firstCompletelyVisibleViewPosition == -1 || firstCompletelyVisibleViewPosition == null){
                return super.findSnapView(layoutManager)
            }else{
                return lm.findViewByPosition(firstCompletelyVisibleViewPosition)
            }
        }else{
            return super.findSnapView(layoutManager)
        }



    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View,
    ): IntArray {
        val out = IntArray(2)

        out[0] = layoutManager.getDecoratedLeft(targetView) - layoutManager.paddingLeft
        out[1] = layoutManager.getDecoratedTop(targetView) - layoutManager.paddingTop

        if (layoutManager.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            out[0] = (layoutManager.getDecoratedRight(targetView) - layoutManager.paddingRight) - layoutManager.width
            out[1] = layoutManager.getDecoratedTop(targetView) - layoutManager.paddingTop
        }

        return out
    }


}