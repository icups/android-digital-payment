package com.mnc.app.listener

import android.view.animation.Animation

abstract class AnimationListener : Animation.AnimationListener {

    override fun onAnimationStart(anim: Animation?) {
        onAnimationStart()
    }

    override fun onAnimationRepeat(anim: Animation?) {
        onAnimationRepeat()
    }

    override fun onAnimationEnd(anim: Animation?) {
        onAnimationEnd()
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    open fun onAnimationStart() {}
    open fun onAnimationRepeat() {}
    open fun onAnimationEnd() {}

}