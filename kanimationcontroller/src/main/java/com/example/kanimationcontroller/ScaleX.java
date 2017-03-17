package com.example.kanimationcontroller;

import android.animation.Animator;
import android.view.View;

/**
 * Created by kognsin on 9/27/2016.
 */

public class ScaleX implements IAnimateSet{
    private float value;

    @Override
    public float getValue() {
        return value;
    }

    public ScaleX(float value){
        this.value = value;
    }

    @Override
    public void setView(View view) {
        view.setScaleX(value);
    }

    @Override
    public void animateView(View view) {
        view.animate().scaleX(value);
    }

    @Override
    public void setValue(float... value) {
        this.value = value[0];
    }
}
