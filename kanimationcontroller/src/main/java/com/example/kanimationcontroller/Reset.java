package com.example.kanimationcontroller;

import android.animation.Animator;
import android.view.View;

/**
 * Created by kognsin on 9/27/2016.
 */

public class Reset implements IAnimateSet {

    @Override
    public void setView(final View view) {
        view.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setX(0);
                view.setY(0);
                view.setScaleY(1.0F);
                view.setScaleX(1.0F);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void animateView(final View view) {
        view.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setX(0);
                view.setY(0);
                view.setScaleY(1.0F);
                view.setScaleX(1.0F);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    public float getValue() {
        return 0;
    }

    @Override
    public void setValue(float... value) {

    }

}
