package com.imagine.android_imagine_tools.tools.ui.views.fadingImageView

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import com.imagine.android_imagine_tools.tools.R


class FadingImageView @JvmOverloads constructor(
    context: Context, private val attr: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attr, defStyleAttr) {

    private val imageViewMain: ImageView by lazy { findViewById(R.id.imageViewMain) }
    private val imageViewRight1: ImageView by lazy { findViewById(R.id.imageViewRight1) }
    private val imageViewRight2: ImageView by lazy { findViewById(R.id.imageViewRight2) }
    private val imageViewRight3: ImageView by lazy { findViewById(R.id.imageViewRight3) }
    private val imageViewRight4: ImageView by lazy { findViewById(R.id.imageViewRight4) }
    private val imageViewLeft1: ImageView by lazy { findViewById(R.id.imageViewLeft1) }
    private val imageViewLeft2: ImageView by lazy { findViewById(R.id.imageViewLeft2) }
    private val imageViewLeft3: ImageView by lazy { findViewById(R.id.imageViewLeft3) }
    private val imageViewLeft4: ImageView by lazy { findViewById(R.id.imageViewLeft4) }

    private var imageDrawable: Drawable? = null
    private var isCircle: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_fading_image_view, this, true)

        setViewAttributes()
        setupUI()



        setupAnimation()


    }

    /**
     * Get attributes passed in xml
     * */
    private fun setViewAttributes() {
        val arr: TypedArray = context.obtainStyledAttributes(attr, R.styleable.FadingImageView)
        imageDrawable = ContextCompat.getDrawable(
            context,
            arr.getResourceId(R.styleable.FadingImageView_imageDrawable, R.drawable.ic_filter)
        )
        isCircle = arr.getBoolean(
            R.styleable.FadingImageView_ImageIsCircle, false
        )
        arr.recycle()
    }

    private fun setupAnimation() {
        val animatorSet = AnimatorSet()

        if (isCircle) {
            val animatorList = createCircleAnimators(3000, 750)
            animatorSet.playTogether(animatorList)
            animatorSet.start()
        } else {
            val animatorList = createAnimators(3000, 750)
            animatorSet.playTogether(animatorList)
            animatorSet.start()
        }

    }

    private fun setupUI() {
        imageViewMain.setImageDrawable(imageDrawable)
        imageViewRight1.setImageDrawable(imageDrawable)
        imageViewRight2.setImageDrawable(imageDrawable)
        imageViewRight3.setImageDrawable(imageDrawable)
        imageViewRight4.setImageDrawable(imageDrawable)
        imageViewLeft1.setImageDrawable(imageDrawable)
        imageViewLeft2.setImageDrawable(imageDrawable)
        imageViewLeft3.setImageDrawable(imageDrawable)
        imageViewLeft4.setImageDrawable(imageDrawable)
    }

    private fun createAnimators(baseDuration: Long, offsetDuration: Long): List<ValueAnimator> {
        var duration = baseDuration

        val rightImageViewList =
            listOf<ImageView>(imageViewRight1, imageViewRight2, imageViewRight3, imageViewRight4)
        val leftImageViewList =
            listOf<ImageView>(imageViewLeft1, imageViewLeft2, imageViewLeft3, imageViewLeft4)
        val animatorList: MutableList<ValueAnimator> = mutableListOf()

        for (imageView in rightImageViewList) {

            val marginAnimator = ValueAnimator.ofInt(imageView.marginEnd, 0)
            val alphaAnimator = ValueAnimator.ofFloat(1.0f, 0.0f)

            marginAnimator.repeatMode = ValueAnimator.RESTART
            marginAnimator.repeatCount = ValueAnimator.INFINITE

            alphaAnimator.repeatMode = ValueAnimator.RESTART
            alphaAnimator.repeatCount = ValueAnimator.INFINITE

            duration += offsetDuration
            marginAnimator.duration = duration
            alphaAnimator.duration = duration
            marginAnimator.startDelay = duration - baseDuration
            alphaAnimator.startDelay = duration - baseDuration

            marginAnimator.addUpdateListener {
                setMarginStartOrEnd(imageView, (it.animatedValue as Int), true)
            }


            alphaAnimator.addUpdateListener {

                imageView.alpha = it.animatedValue as Float
            }

            animatorList.add(marginAnimator)
            animatorList.add(alphaAnimator)

        }

        duration = baseDuration

        for (imageView in leftImageViewList) {

            val marginAnimator = ValueAnimator.ofInt(imageView.marginStart, 0)
            val alphaAnimator = ValueAnimator.ofFloat(1.0f, 0.0f)

            marginAnimator.repeatMode = ValueAnimator.RESTART
            marginAnimator.repeatCount = ValueAnimator.INFINITE

            alphaAnimator.repeatMode = ValueAnimator.RESTART
            alphaAnimator.repeatCount = ValueAnimator.INFINITE

            duration += offsetDuration
            marginAnimator.duration = duration
            alphaAnimator.duration = duration
            marginAnimator.startDelay = duration - baseDuration
            alphaAnimator.startDelay = duration - baseDuration

            marginAnimator.addUpdateListener {
                setMarginStartOrEnd(imageView, (it.animatedValue as Int), false)
            }

            alphaAnimator.addUpdateListener {

                imageView.alpha = it.animatedValue as Float
            }

            animatorList.add(marginAnimator)
            animatorList.add(alphaAnimator)

        }



        return animatorList

    }

    private fun createCircleAnimators(
        baseDuration: Long,
        offsetDuration: Long
    ): List<ValueAnimator> {
        var duration = baseDuration

        val rightImageViewList =
            listOf<ImageView>(imageViewRight1, imageViewRight2, imageViewRight3, imageViewRight4)
        val leftImageViewList =
            listOf<ImageView>(imageViewLeft1, imageViewLeft2, imageViewLeft3, imageViewLeft4)
        val animatorList: MutableList<ValueAnimator> = mutableListOf()

        //hide left imageview since we wont need them for a circle image
        leftImageViewList.forEach { it.visibility = View.GONE }

        for (imageView in rightImageViewList) {

            val marginAnimator = ValueAnimator.ofFloat(1f, 1.5f)
            val alphaAnimator = ValueAnimator.ofFloat(1.0f, 0.0f)

            marginAnimator.repeatMode = ValueAnimator.RESTART
            marginAnimator.repeatCount = ValueAnimator.INFINITE

            alphaAnimator.repeatMode = ValueAnimator.RESTART
            alphaAnimator.repeatCount = ValueAnimator.INFINITE

            duration += offsetDuration
            marginAnimator.duration = duration
            alphaAnimator.duration = duration
            marginAnimator.startDelay = duration - baseDuration
            alphaAnimator.startDelay = duration - baseDuration

            marginAnimator.addUpdateListener {

                imageView.scaleX = it.animatedValue as Float
                imageView.scaleY = it.animatedValue as Float
            }


            alphaAnimator.addUpdateListener {

                imageView.alpha = it.animatedValue as Float
            }

            animatorList.add(marginAnimator)
            animatorList.add(alphaAnimator)

        }

        return animatorList
    }

    private fun setMarginStartOrEnd(v: View, margin: Int, isEnd: Boolean) {
        if (v.layoutParams is MarginLayoutParams) {
            val p = v.layoutParams as MarginLayoutParams

            if (isEnd) {
                p.marginEnd = margin
            } else {
                p.marginStart = margin
            }
            v.layoutParams = p
            v.requestLayout()
        }
    }

    fun setImage(@DrawableRes drawableRes: Int) {
        imageDrawable = ContextCompat.getDrawable(context, drawableRes)
        invalidate()
    }

}