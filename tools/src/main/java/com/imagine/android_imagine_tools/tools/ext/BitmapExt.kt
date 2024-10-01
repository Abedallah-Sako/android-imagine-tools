package com.imagine.android_imagine_tools.tools.ext

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import kotlin.math.max
import kotlin.math.min


fun Bitmap.drawCircle(circleColor:String,iconColor:String):Bitmap{
    val sizeIncreaseParameter = 30.toPx
    var overlayWidth = this.width +sizeIncreaseParameter
    var overlayHeight = this.height +sizeIncreaseParameter

    if(overlayWidth != overlayHeight){
        val maxDimension = max(overlayWidth, overlayHeight)
        overlayWidth = maxDimension
        overlayHeight = maxDimension
    }


    val bmOverlay = Bitmap.createBitmap(overlayWidth, overlayHeight, Bitmap.Config.ARGB_8888)


    val canvas = Canvas(bmOverlay)

    //circle
    val circlePaint = Paint()
    circlePaint.color = Color.parseColor(circleColor)


    val circleCX =  sizeIncreaseParameter/2 + this.width/2f
    val circleCY = sizeIncreaseParameter/2 + this.height/2f
    val circleR = ((min(this.height,this.width))/2f + sizeIncreaseParameter/2)*0.95f

    canvas.drawCircle(circleCX, circleCY, circleR,circlePaint);

    //icon
//    val iconX = sizeIncreaseParameter.toFloat()/2
    val iconX = circleCX - this.width / 2f
//    val iconY = sizeIncreaseParameter.toFloat()/2
    val iconY = circleCY - this.height / 2f

    val iconPaint = Paint()
    iconPaint.colorFilter = PorterDuffColorFilter(Color.parseColor(iconColor), PorterDuff.Mode.SRC_IN)


    val top = circleCY - circleR
    val bottom = circleCY + circleR
    val left = circleCX - circleR
    val right = circleCX + circleR
    val subtractionVal = circleR/2

    val dst = RectF(left+subtractionVal, top+subtractionVal, right-subtractionVal, bottom-subtractionVal)

//    canvas.drawBitmap(this.copy(Bitmap.Config.ARGB_8888,false),iconX,iconY,iconPaint)
    canvas.drawBitmap(this.copy(Bitmap.Config.ARGB_8888,false),null,dst,iconPaint)


    return bmOverlay
}