package com.example.kanimationcontroller;

import android.animation.Animator;

/**
 * Created by DroidDev on 3/8/17.
 */

public abstract class CustomAnimatorCallback implements Animator.AnimatorListener {

    private BaseAnimationControl animationControl;

    public CustomAnimatorCallback(BaseAnimationControl animationControl){
        this.animationControl = animationControl;
    }

    public abstract void onAnimationEnd(Animator animator, BaseAnimationControl animationControl);

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        onAnimationEnd(animator, this.animationControl);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
