package com.imagine.android_imagine_tools.tools.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.transition.Explode
import androidx.transition.TransitionValues

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class ExplodeFadeOut() : Explode() {
    init {
        propagation = null
    }
    private var mFadeDuration:Long? = null
    private var mTransitionDuration:Long? = null
    fun setFadeDuration(fadeDuration:Long){
        mFadeDuration = fadeDuration
    }

    fun setTransitionDuration(transitionDuration:Long){
        mTransitionDuration = transitionDuration
    }

    override fun onAppear(
        sceneRoot: ViewGroup,
        view: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        val explodeAnimator = super.onAppear(sceneRoot, view, startValues, endValues)
        val fadeInAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)

        if(mFadeDuration !=null){
            fadeInAnimator.duration = mFadeDuration?:300L
        }

        if(mTransitionDuration!=null){
            explodeAnimator?.duration = mTransitionDuration?:300L
        }

        return animatorSet(explodeAnimator?:fadeInAnimator, fadeInAnimator)

    }    override fun onDisappear(
        sceneRoot: ViewGroup,
        view: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        val explodeAnimator = super.onDisappear(sceneRoot, view, startValues, endValues)
        val fadeOutAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f)


        if(mFadeDuration !=null){
            fadeOutAnimator.duration = mFadeDuration?:300L
        }
        if(mTransitionDuration!=null){
            explodeAnimator?.duration = mTransitionDuration?:300L
        }

        return animatorSet(explodeAnimator?:fadeOutAnimator, fadeOutAnimator)
    }    private fun animatorSet(explodeAnimator: Animator, fadeAnimator: Animator): AnimatorSet {
        val animatorSet = AnimatorSet()
        animatorSet.play(explodeAnimator).with(fadeAnimator)
        return animatorSet
    }
}