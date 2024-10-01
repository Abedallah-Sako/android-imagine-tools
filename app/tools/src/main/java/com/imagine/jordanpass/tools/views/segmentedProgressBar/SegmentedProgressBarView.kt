package com.imagine.jordanpass.tools.views.segmentedProgressBar

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.layoutDirection
import androidx.window.layout.WindowMetricsCalculator
import com.imagine.jordanpass.tools.R
import com.imagine.jordanpass.tools.ext.logd
import com.imagine.jordanpass.tools.ext.toPx
import java.util.Locale


class SegmentedProgressBarView @JvmOverloads constructor(
    context: Context,
    private val attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attr, defStyleAttr) {
    var segmentCount = 4 //number of segments
    var segmentsSpacing = 5f //spacing between segments
    var barColor = Color.parseColor("#e8efee") //segments color
    var segmentAlpha = 1f //segments alpha
    var drawableWidth = 25.toPx
    var drawableHeight = 25.toPx
    var drawableWidthPositionDenominator = 2f
    var gradientColor1 = Color.parseColor("#c2c1c0")
    var gradientColor2 = Color.parseColor("#db1f2a")
    var gradientColor3 = Color.parseColor("#000000")
    var solidProgressColor = 0
    var enableDrawable = false

    private var progressCheckCirclePosition: SegmentCoordinates? = null

    private val segmentPaths: MutableList<Path> = mutableListOf()
    private val segmentPaints: MutableList<Paint> = mutableListOf()

    init {
        setViewAttributes()


        (0 until segmentCount).forEach { _ ->
            segmentPaths.add(Path())
            segmentPaints.add(Paint(Paint.ANTI_ALIAS_FLAG))
        }

    }

    /**
     * Get attributes (ex: segmentCount,SegmentSpacing) passed in xml
     * */
    private fun setViewAttributes() {
        val arr: TypedArray =
            context.obtainStyledAttributes(attr, R.styleable.SegmentedProgressBarView)

        segmentCount = arr.getInt(R.styleable.SegmentedProgressBarView_segmentCount, 4)
        segmentsSpacing = arr.getFloat(R.styleable.SegmentedProgressBarView_segmentSpacing, 5f)
        barColor = arr.getColor(
            R.styleable.SegmentedProgressBarView_defaultSegmentColor, Color.parseColor("#e8efee")
        )
        segmentAlpha = arr.getFloat(R.styleable.SegmentedProgressBarView_segmentAlpha, 1f)
        drawableWidth =
            arr.getDimensionPixelSize(R.styleable.SegmentedProgressBarView_drawableWidth, 25.toPx)
        drawableWidthPositionDenominator =
            arr.getFloat(R.styleable.SegmentedProgressBarView_drawableWidthPositionDominator, 2f)
        gradientColor1 = arr.getColor(
            R.styleable.SegmentedProgressBarView_gradientColor1, Color.parseColor("#c2c1c0")
        )
        gradientColor2 = arr.getColor(
            R.styleable.SegmentedProgressBarView_gradientColor2, Color.parseColor("#db1f2a")
        )
        gradientColor3 = arr.getColor(
            R.styleable.SegmentedProgressBarView_gradientColor3, Color.parseColor("#000000")
        )
        solidProgressColor =
            arr.getColor(R.styleable.SegmentedProgressBarView_solidProgressColor, 0)

        enableDrawable = arr.getBoolean(R.styleable.SegmentedProgressBarView_enableDrawable,false)
        arr.recycle()

    }

    override fun onDraw(canvas: Canvas?) {

        (0 until segmentCount).forEach { index ->
            val path = segmentPaths[index]
            val paint = segmentPaints[index]

            val segmentCoordinates =
                computeSegmentCoordinates(index, width.toFloat(), segmentsSpacing)

            drawSegment(canvas, path, paint, segmentCoordinates, barColor, segmentAlpha)

        }




        //draw circle check drawable
        if(enableDrawable) {
            canvas?.save()
            if (progressCheckCirclePosition != null) {
                val d = ContextCompat.getDrawable(context, R.drawable.ic_check_circle)



                d?.setBounds(0, 0, 30.toPx, 30.toPx)


                if (Locale.getDefault().layoutDirection == LAYOUT_DIRECTION_RTL) {
                    canvas?.translate(
                        progressCheckCirclePosition!!.topRightX - (drawableWidth / drawableWidthPositionDenominator) + 30.toPx,
                        0f
                    )
                    canvas?.scale(-1f, 1f)
                } else {
                    canvas?.translate(
                        progressCheckCirclePosition!!.topRightX - drawableWidth / drawableWidthPositionDenominator,
                        0f
                    )
                }
                d?.draw(canvas!!)
            }
            canvas?.restore()
        }

    }

    private fun computeSegmentCoordinates(
        position: Int,
        width: Float,
        spacing: Float,
    ): SegmentCoordinates {

        val segmentWidth = (width - spacing * (segmentCount - 1)) / segmentCount
        val isLast = position == segmentCount - 1

        val topLeft = (segmentWidth + spacing) * position
        val bottomLeft = (segmentWidth + spacing) * position
        var topRight = segmentWidth * (position + 1) + spacing * position

        //draw last segment just a little bit shorter so circle check can fit
        if (isLast) {
            topRight = segmentWidth * (position + 1) + spacing * position - 3f
        }

        val bottomRight = segmentWidth * (position + 1) + spacing * position


        return SegmentCoordinates(topLeft, topRight, bottomLeft, bottomRight)

    }

    private fun drawSegment(
        canvas: Canvas?,
        path: Path,
        paint: Paint,
        coordinates: SegmentCoordinates,
        color: Int,
        alpha: Float,
    ) {

        path.run {
            reset()
            moveTo(coordinates.topLeftX, height.toFloat() / 4)
            lineTo(coordinates.topRightX, height.toFloat() / 4)
            lineTo(coordinates.topRightX, height.toFloat() / 2)
            lineTo(coordinates.topLeftX, height.toFloat() / 2)
            close()
        }

        paint.color = color
        paint.alpha = toAlphaFloat(alpha)

        canvas?.drawPath(path, paint)


    }

    private fun toAlphaFloat(float: Float): Int {
        return (float * 255).toInt()
    }

    fun setProgress(progress: Int, activity: Activity) {
        resetProgress()

        var mWidth = width


        if (mWidth == 0) {
            val windowMetrics =
                WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
            val currentBounds = windowMetrics.bounds

            mWidth = currentBounds.width()
            mWidth -= (30.toPx)
        }

        if (progress <= segmentCount) {

            (0 until progress).forEach { index ->
                segmentPaints[index].shader = LinearGradient(
                    0f, 0f, mWidth.toFloat(), 0f, intArrayOf(
                        Color.parseColor("#c2c1c0"),
                        Color.parseColor("#db1f2a"),
                        Color.parseColor("#000000")
                    ), null, Shader.TileMode.MIRROR
                )

            }

            if (progress != 0) {
                progressCheckCirclePosition =
                    computeSegmentCoordinates(progress - 1, mWidth.toFloat(), segmentsSpacing)
            }

            //shift circle check mark on the last item so it doesn't go out of the screen
            if (progress == segmentCount) {
                drawableWidthPositionDenominator = 0.865f
            } else {
                drawableWidthPositionDenominator = 2f
            }

            invalidate()
        }
    }

    fun setProgressToSegment(segmentIndex: Int, activity: Activity) {
        if (solidProgressColor == 0) return

        resetProgress()

        var mWidth = width


        if (mWidth == 0) {
            val windowMetrics =
                WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
            val currentBounds = windowMetrics.bounds

            mWidth = currentBounds.width()
            mWidth -= (30.toPx)
        }

        if (segmentIndex <= segmentCount-1) {

            segmentPaints[segmentIndex].colorFilter =
                PorterDuffColorFilter(solidProgressColor, PorterDuff.Mode.SRC)
            segmentPaints[segmentIndex].color = solidProgressColor

            progressCheckCirclePosition =
                computeSegmentCoordinates(segmentIndex, mWidth.toFloat(), segmentsSpacing)

            //shift circle check mark on the last item so it doesn't go out of the screen
            if (segmentIndex == segmentCount) {
                drawableWidthPositionDenominator = 0.865f
            } else {
                drawableWidthPositionDenominator = 2f
            }

            invalidate()
        }
    }

    private fun resetProgress() {


        (0 until segmentCount).forEach { index ->
            segmentPaints[index].shader = null
            segmentPaints[index].colorFilter = null

        }

        invalidate()

    }


    private data class SegmentCoordinates(
        val topLeftX: Float,
        val topRightX: Float,
        val bottomLeftY: Float,
        val bottomRightY: Float,
    )


}