package co.za.rain.myapplication.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation


fun View.blinkView(fromAlpha: Float, toAlpha: Float, duration: Long, startOffset: Long, repeatMode: Int, repeatCount: Int, onAnimationFinished: () -> Unit = {}, onAnimationStarted: () -> Unit = {}, onAnimationRepeated: () -> Unit = {}){
    val anim = getblinkViewAnination(fromAlpha, toAlpha, duration, startOffset, repeatMode, repeatCount, onAnimationFinished, onAnimationStarted, onAnimationRepeated)
    this.startAnimation(anim)
}

fun View.getblinkViewAnination(fromAlpha: Float, toAlpha: Float, duration: Long, startOffset: Long, repeatMode: Int, repeatCount: Int, onAnimationFinished: () -> Unit = {}, onAnimationStarted: () -> Unit = {}, onAnimationRepeated: () -> Unit = {}): AlphaAnimation {
    val anim = AlphaAnimation(fromAlpha, toAlpha)
    anim.duration = duration
    anim.startOffset = startOffset
    anim.repeatMode = repeatMode
    anim.repeatCount = repeatCount

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            onAnimationStarted()
        }

        override fun onAnimationEnd(animation: Animation) {
            onAnimationFinished()
        }

        override fun onAnimationRepeat(animation: Animation) {
            onAnimationRepeated()
        }
    })


    return anim
}

fun View.rotateView(fromDegrees: Float, toDegrees: Float, pivotXValue: Float, pivotYValue: Float, duration: Long, startOffset: Long, repeatMode: Int, repeatCount: Int, onAnimationFinished: () -> Unit = {}, onAnimationStarted: () -> Unit = {}, onAnimationRepeated: () -> Unit = {} ){
    val anim = getRotateView(fromDegrees, toDegrees, pivotXValue, pivotYValue, duration, startOffset, repeatMode, repeatCount, onAnimationFinished, onAnimationStarted, onAnimationRepeated)
    this.startAnimation(anim)
}

fun View.getRotateView(fromDegrees: Float, toDegrees: Float, pivotXValue: Float, pivotYValue: Float, duration: Long, startOffset: Long, repeatMode: Int, repeatCount: Int, onAnimationFinished: () -> Unit = {}, onAnimationStarted: () -> Unit = {}, onAnimationRepeated: () -> Unit = {}): RotateAnimation {

    val anim = RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, pivotXValue, Animation.RELATIVE_TO_SELF, pivotYValue)
    anim.interpolator = LinearInterpolator()
    anim.duration = duration
    anim.startOffset = startOffset
    anim.repeatMode = repeatMode
    anim.repeatCount = repeatCount

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            onAnimationStarted()
        }

        override fun onAnimationEnd(animation: Animation) {
            onAnimationFinished()
        }

        override fun onAnimationRepeat(animation: Animation) {
            onAnimationRepeated()
        }
    })

    return anim
}
