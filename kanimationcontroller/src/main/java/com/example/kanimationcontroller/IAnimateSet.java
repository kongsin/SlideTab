package com.example.kanimationcontroller;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by kognsin on 9/27/2016.
 */

public interface IAnimateSet {
    void setView(View view);
    void animateView(View view);
    float getValue();
    void setValue(float...value);
}
