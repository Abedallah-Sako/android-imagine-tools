package com.imagine.jordanpass.tools.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs


class CenterZoomLinearLayoutManager(context: Context?,orientation: Int = RecyclerView.VERTICAL, reverseLayout: Boolean = false,private val rotateItems:Boolean = false): LinearLayoutManager(context) {

    private val mShrinkAmount = 0.50f
    private val mShrinkDistance = 0.9f

    init {
        this.orientation = orientation
        this.reverseLayout = reverseLayout
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleChildren()
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: RecyclerView.State?): Int {
        val orientation = orientation
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val midpoint = height / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child: View? = getChildAt(i)
                if(child!=null) {
                    val childMidpoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2f
                    val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                    val dinv = Math.min(d1, midpoint - childMidpoint)
                    val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                    child.scaleX = scale
                    child.scaleY = scale

                    child.alpha = scale



                    if(rotateItems) {

                        if (dinv > 0.0f) {
                            child.rotationX = -abs(scale * 100) + 100

                        } else {
                            child.rotationX = abs(scale * 100) - 100
                        }
                    }


                }
            }
            scrolled
        } else {
            0
        }
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: Recycler?,
        state: RecyclerView.State?,
    ): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val midpoint = width / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child: View? = getChildAt(i)
                if(child!=null){
                    val childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f
                    val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                    val dinv = Math.min(d1, midpoint - childMidpoint)
                    val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                    child.scaleX = scale
                    child.scaleY = scale

                    if(rotateItems) {

                        if (dinv > 0.0f) {
                            child.rotationY = -abs(scale * 100) + 100

                        } else {
                            child.rotationY = abs(scale * 100) - 100
                        }
                    }
                }

            }
            scrolled
        } else {
            0
        }
    }

    private fun scaleChildren() {
        val midpoint = height / 2f
        val d0 = 0f
        val d1 = mShrinkDistance * midpoint
        val s0 = 1f
        val s1 = 1f - mShrinkAmount
        for (i in 0 until childCount) {
            val child: View? = getChildAt(i)

            if(child!=null) {
                val childMidpoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2f
                val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                val dinv = Math.min(d1, midpoint - childMidpoint)

                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale


                if(rotateItems) {

                    if (dinv > 0.0f) {
                        child.rotationX = -abs(scale * 100) + 100

                    } else {
                        child.rotationX = abs(scale * 100) - 100
                    }
                }
            }
        }

    }

}