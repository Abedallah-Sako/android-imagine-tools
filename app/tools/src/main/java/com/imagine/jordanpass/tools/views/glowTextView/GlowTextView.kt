package com.imagine.jordanpass.tools.views.glowTextView

import android.R.id.text2
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable.ShaderFactory
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.imagine.jordanpass.tools.R


class GlowTextView @JvmOverloads constructor(
    private val context: Context, private val attr: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : FrameLayout(context, attr, defStyleAttr) {

    private var glowColor: Int = Color.CYAN
    private var textColor: Int = Color.WHITE
    private var textSize = 100f
    private var glowRadius = 200f
    private var startColorAlpha = 100
    private var endColorAlpha = 0
    private var text = "Hello"
    private var typeFace:Typeface? = null


    private val glowPaintList = mutableListOf<Paint>()

    private val glowTextView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_glow_text_view, this, true)
        glowTextView = findViewById(R.id.glow_text_view)

        setViewAttributes()
        setupUI()

        for (i in 0 until (text.length)) {
            glowPaintList.add(Paint(Paint.ANTI_ALIAS_FLAG))
        }

    }

    /**
     * Get attributes passed in xml
     * */
    private fun setViewAttributes() {
        val arr: TypedArray = context.obtainStyledAttributes(attr, R.styleable.GlowTextView)

        glowColor = arr.getColor(R.styleable.GlowTextView_glowColor,Color.WHITE)
        textColor = arr.getColor(R.styleable.GlowTextView_textColor,Color.BLACK)
        textSize = arr.getDimension(R.styleable.GlowTextView_textSize,50f)
        glowRadius = arr.getDimension(R.styleable.GlowTextView_glowRadius,200f)
        startColorAlpha = ((arr.getFloat(R.styleable.GlowTextView_startColorAlpha,1f)) * 255).toInt()
        endColorAlpha = ((arr.getFloat(R.styleable.GlowTextView_endColorAlpha,0f)) * 255).toInt()
        text = arr.getString(R.styleable.GlowTextView_text)?:"Hello"

        if(arr.getResourceId(R.styleable.GlowTextView_fontFamily,0)!= 0){
            typeFace = ResourcesCompat.getFont(context,arr.getResourceId(R.styleable.GlowTextView_fontFamily,0))
        }


        arr.recycle()
    }

    private fun setupUI(){

        glowTextView.text = text
        glowTextView.textSize = textSize
        glowTextView.setTextColor(textColor)
        glowTextView.typeface = typeFace

    }

    override fun dispatchDraw(canvas: Canvas?) {

        glowTextView.text.forEachIndexed { index, c ->
            val point = glowTextView.charLocation(index+1)
            drawGlowBackground(glowPaintList[index],point?.x?.toFloat()?:1f,point?.y?.toFloat()?:1f,canvas)
        }

        drawGlowBackground(glowPaintList[0],1f,1f,canvas)


        super.dispatchDraw(canvas)


    }



    private fun drawGlowBackground(glowPaint: Paint, x:Float,y:Float, canvas: Canvas?) {
        glowPaint.shader = shaderFactory(x+paddingStart, y+(glowTextView.layout.height/2)+paddingTop)
        glowPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
        canvas?.drawPaint(glowPaint)
    }

    private fun shaderFactory(centerX: Float, centerY: Float): Shader {


        return RadialGradient(
            centerX ,
            centerY,
            glowRadius,
            intArrayOf(glowColor.setAlpha(startColorAlpha), glowColor.setAlpha(endColorAlpha)),
            null,
            Shader.TileMode.CLAMP
        )
    }

    private fun TextView.charLocation(offset: Int): Point? {
        layout ?: return null // Layout may be null right after change to the text view

        val lineOfText = layout.getLineForOffset(offset)
        val xCoordinate = layout.getPrimaryHorizontal(offset).toInt()
        val yCoordinate = layout.getLineTop(lineOfText)
        return Point(xCoordinate, yCoordinate)
    }


    @ColorInt
    private fun Int.setAlpha(alpha: Int): Int =
        Color.argb(alpha, Color.red(this), Color.green(this), Color.blue(this))


    fun setGlowColorForLetter(index: Int,color:Int){
        glowPaintList[index].colorFilter = PorterDuffColorFilter(color,PorterDuff.Mode.MULTIPLY)
        invalidate()
    }

    private var previousSpan:Spannable = SpannableString(text)
    fun setLetterColor(index: Int,color:Int){
        if(index<text.length){
            val spannable: Spannable = previousSpan

            spannable.setSpan(
                ForegroundColorSpan(color),
                index,
                index+1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )

            previousSpan = spannable
            glowTextView.text = spannable

        }

    }

}