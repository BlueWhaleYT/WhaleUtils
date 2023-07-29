package com.bluewhaleyt.common.animation

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import androidx.annotation.AnimRes
import com.bluewhaleyt.common.common.waitFor

class AnimationUtils(
    private val context: Context
) {

    val DURATION_SHORT = 300L
    val DURATION_LONG = 600L

    fun applyAnimation(view: View, @AnimRes resId: Int) {
        val anim = AnimationUtils.loadAnimation(context, resId)
        view.startAnimation(anim)
    }

    fun applyAnimation(
        view: View,
        animation: Animations,
        duration: Long = DURATION_SHORT,
        vararg params: Float,
        action: () -> Unit = {},
    ) {
        when (animation) {
            Animations.LEFT_TO_RIGHT -> translate(view, duration, fromX = -1f) {action()}
            Animations.RIGHT_TO_LEFT -> translate(view, duration, fromX = 1f) {action()}
            Animations.TOP_TO_BOTTOM -> translate(view, duration, fromY = -1f) {action()}
            Animations.BOTTOM_TO_TOP -> translate(view, duration, fromY = 1f) {action()}
            Animations.BOUNCE_IN -> {
                val fromX = set(0, 0.3f, *params)
                val fromY = set(1, 0.3f, *params)
                scale(view, duration, fromX = fromX, fromY = fromY, interpolator = BounceInterpolator()) {action()}
            }
            Animations.BOUNCE_OUT -> {
                val toX = set(0, 0.3f, *params)
                val toY = set(1, 0.3f, *params)
                scale(view, duration, toX = toX, toY = toY, interpolator = BounceInterpolator()) {action()}
            }
            else -> {}
        }
    }

    private fun set(index: Int, defaultValue: Float, vararg params: Float): Float {
        return if (index == 0) {
            if (params.isNotEmpty()) params[0] else defaultValue
        } else if (index > 0) {
            if (params.size > index) params[index] else defaultValue
        } else 0f
    }

    fun translate(
        view: View,
        duration: Long,
        fromX: Float = 0f,
        toX: Float = 0f,
        fromY: Float = 0f,
        toY: Float = 0f,
        action: () -> Unit
    ) {
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, fromX,
            Animation.RELATIVE_TO_SELF, toX,
            Animation.RELATIVE_TO_SELF, fromY,
            Animation.RELATIVE_TO_SELF, toY
        )
        setupAnimation(view, animation, duration, action)
        view.startAnimation(animation)
    }

    fun scale(
        view: View,
        duration: Long,
        fromX: Float = 1f,
        toX: Float = 1f,
        fromY: Float = 1f,
        toY: Float = 1f,
        pivotX: Float = 0.5f,
        pivotY: Float = 0.5f,
        interpolator: Interpolator? = null,
        action: () -> Unit
    ) {
        val animation = ScaleAnimation(
            fromX, toX,
            fromY, toY,
            Animation.RELATIVE_TO_SELF, pivotX,
            Animation.RELATIVE_TO_SELF, pivotY
        )
        setupAnimation(view, animation, duration, action)
        animation.interpolator = interpolator
        view.startAnimation(animation)
    }

    private fun setupAnimation(view: View, animation: Animation, duration: Long, action: () -> Unit) {
        animation.duration = duration
        view.startAnimation(animation)
        context.waitFor(duration) { action() }
    }
}