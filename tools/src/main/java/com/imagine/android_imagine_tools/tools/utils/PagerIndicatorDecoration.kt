package com.imagine.android_imagine_tools.tools.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.annotation.ColorInt
import androidx.core.text.layoutDirection
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imagine.android_imagine_tools.tools.ext.toPx
import java.util.Locale


/**
 * A Dot Indicator Decoration for recycler view
 *
 * @param selectedColor selected dot color
 * @param unSelectedColor all unselected dot color
 * @param bottomMarginInPx bottom margin in Px
 *
 * */
class PagerIndicatorDecoration(
    @ColorInt private val selectedColor:Int,@ColorInt  private val unSelectedColor:Int, private val bottomMarginInPx:Int =4.toPx
) : RecyclerView.ItemDecoration() {
    var mIndicatorHeight = 33.toPx
    private var mIndicatorItemLength = (4.toPx).toFloat()
    private var mIndicatorItemPadding = (16.toPx).toFloat()
    private var mPaint = Paint()

    private val mInterpolator: Interpolator = AccelerateDecelerateInterpolator()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        //flip decoration if RTL
        if (Locale.getDefault().layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            c.scale(-1f, 1f, parent.width.div(2f), parent.height / 2f)
        }


        val itemCount: Int = parent.adapter?.itemCount ?: 0

        // center horizontally, calculate width and subtract half from center
        val totalLength: Float = mIndicatorItemLength * itemCount
        val paddingBetweenItems: Float = Math.max(0, itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX: Float = (parent.width - indicatorTotalWidth) / 2f

        // put indicator at the bottom
        val indicatorPosY: Float = parent.height.toFloat() - bottomMarginInPx

        drawInactiveIndicators(parent.context, c, indicatorStartX, indicatorPosY, itemCount)


        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val activePosition = layoutManager.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }


        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)


        val left = activeChild?.left
        val width = activeChild?.width


        // interpolate offset for smooth animation
        val progress: Float =
            mInterpolator.getInterpolation((left?.times(-1) ?: 0) / ((width ?: 0).toFloat()))


        drawHighlights(
            parent.context, c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount
        )



    }

    private fun drawInactiveIndicators(
        context: Context,
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int,
    ) {
        //dot color not selected
        mPaint.color = unSelectedColor

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
//            c.drawLine(start, indicatorPosY,
//                start + mIndicatorItemLength, indicatorPosY, mPaint)
            c.drawCircle(start, indicatorPosY, mIndicatorItemLength, mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        context: Context,
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float, itemCount: Int,
    ) {
        //dot color selected
        mPaint.color = selectedColor

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition

            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength, mPaint)
        } else {


            val highlightStart = indicatorStartX + itemWidth * highlightPosition

            val partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress

            c.drawCircle(
                highlightStart + partialLength, indicatorPosY, mIndicatorItemLength, mPaint
            )

            // draw the cut off highlight
            /*mPaint.strokeWidth = 8.toPx.toFloat()
            mPaint.strokeCap = Paint.Cap.ROUND
            c.drawLine(highlightStart + partialLength, indicatorPosY, highlightStart + mIndicatorItemLength, indicatorPosY, mPaint)*/


        }
    }

}