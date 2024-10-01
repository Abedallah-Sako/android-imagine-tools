package com.imagine.jordanpass.tools.views.waveView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.imagine.jordanpass.tools.R
import com.imagine.jordanpass.tools.ext.logd
import kotlin.math.hypot

class WavesView @JvmOverloads constructor(context: Context, private val attrs:AttributeSet?= null, private val defStyleAttr:Int = R.style.Widget_WaveView):View(context, attrs, defStyleAttr),
    TiltListener {

    private val wavePaint :Paint
    private val waveGap:Float
    private val wavePath = Path()

    private val gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }
    private val green = Color.RED
    private val gradientColors = intArrayOf(modifyAlpha(green,0.50f),modifyAlpha(green,0.10f),modifyAlpha(green,0.05f))
    private val gradientMatrix = Matrix()

    private var maxRadius =0f
    private var center = PointF(0f,0f)
    private var initialRadius =0f

    private var waveAnimator : ValueAnimator?=null
    private var waveRadiusOffset = 0f
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }

    val tiltSensor = WaveTiltSensor(context)

    init {
        val attr = context.obtainStyledAttributes(attrs,R.styleable.WavesView)

        wavePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = attr.getColor(R.styleable.WavesView_waveColor,0)
            strokeWidth = attr.getDimension(R.styleable.WavesView_waveStrokeWidth,0f)
            style = Paint.Style.STROKE
        }


        waveGap = attr.getDimension(R.styleable.WavesView_waveGap,50f)
        attr.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        waveAnimator = ValueAnimator.ofFloat(0f,waveGap).apply {
            addUpdateListener {
                waveRadiusOffset = it.animatedValue as Float
            }

            duration = 1500L
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }

        tiltSensor.addListener(this)
        tiltSensor.register()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        center.set(w/2f , h/2f)
        maxRadius = hypot(center.x.toDouble(),center.y.toDouble()).toFloat()+1000
        initialRadius = w/waveGap

        //Create gradient after getting sizing information
        gradientPaint.shader = RadialGradient(
            center.x, center.y, maxRadius,
            gradientColors, null, Shader.TileMode.CLAMP
        )

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var currentRadius = initialRadius + waveRadiusOffset

        while (currentRadius < maxRadius){
            val path = createStarPath(currentRadius,wavePath,7)
            canvas?.drawPath(path,wavePaint)
            currentRadius += waveGap
        }

        canvas?.drawPaint(gradientPaint)


    }

    private fun createStarPath(
        radius: Float,
        path: Path = Path(),
        points: Int = 20
    ): Path {

        val mPoints = points*2

        path.reset()
        val pointDelta = 0.7f // difference between the "far" and "close" points from the center
        val angleInRadians = 2.0 * Math.PI / mPoints // essentially 360/20 or 18 degrees, angle each line should be drawn
        val startAngleInRadians = 0.0 //starting to draw star at 0 degrees

        //move pointer to 0 degrees relative to the center of the screen
        path.moveTo(
            center.x + (radius * pointDelta * Math.cos(startAngleInRadians)).toFloat(),
            center.y + (radius * pointDelta * Math.sin(startAngleInRadians)).toFloat()
        )

        //create a line between all the points in the star
        for (i in 1 until mPoints) {
            val hypotenuse = if (i % 2 == 0) {
                //by reducing the distance from the circle every other points, we create the "dip" in the star
                pointDelta * radius
            } else {
                radius
            }

            val nextPointX = center.x + (hypotenuse * Math.cos(startAngleInRadians - angleInRadians * i)).toFloat()
            val nextPointY = center.y + (hypotenuse * Math.sin(startAngleInRadians - angleInRadians * i)).toFloat()
            path.lineTo(nextPointX, nextPointY)
        }

        path.close()
        return path
    }

    private fun updateGradient(x:Float,y:Float){
        gradientMatrix.setTranslate(x-center.x,y-center.y)
        gradientPaint.shader.setLocalMatrix(gradientMatrix)
        postInvalidateOnAnimation()
    }

    private fun modifyAlpha(color: Int, alpha: Float): Int {
        return color and 0x00ffffff or ((alpha * 255).toInt() shl 24)
    }

    override fun onTilt(pitchRollRad: Pair<Double, Double>) {
        val pitchRad = pitchRollRad.first
        val rollRad = pitchRollRad.second

        // Use half view height/width to calculate offset instead of full view/device measurement
        val maxYOffset = center.y.toDouble()
        val maxXOffset = center.x.toDouble()

        val yOffset = (Math.sin(pitchRad) * maxYOffset)
        val xOffset = (Math.sin(rollRad) * maxXOffset)

        updateGradient(xOffset.toFloat() + center.x, yOffset.toFloat() + center.y)
    }

    override fun onDetachedFromWindow() {
        waveAnimator?.cancel()
        tiltSensor.unregister()
        super.onDetachedFromWindow()
    }




}