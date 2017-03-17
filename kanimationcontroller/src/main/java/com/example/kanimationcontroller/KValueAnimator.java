package com.example.kanimationcontroller;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by kongsin on 3/12/2017.
 */

public abstract class KValueAnimator implements IAnimateSet {

    private final int               DEFAULT_DURATION = 250;
    protected static final String   TAG = "KValueAnimator";
    private ValueAnimator           mValueAnimator;
    private View                    mCurrentView;
    protected float[]                 mValue;

    public KValueAnimator(View view, int duration, float...value){
        mValue = value;
        mCurrentView = view;
        mValueAnimator = new ValueAnimator();
        mValueAnimator.setDuration(duration);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                KValueAnimator.this.onAnimationUpdate(animation, mCurrentView);
            }
        });
    }

    public void setAnimationListener(Animator.AnimatorListener listener){
        mValueAnimator.addListener(listener);
    }

    @Override
    public float getValue() {
        return mValue[0];
    }

    @Override
    public void setValue(float... value) {
        mValue = value;
    }

    public void setValueAnimatorProperty(PropertyValuesHolder...valuesHolder){
        mValueAnimator.setValues(valuesHolder);
    }

    public void start(){
        mValueAnimator.setDuration(getAnimationDuration());
        mValueAnimator.start();
    }

    protected int getAnimationDuration(){
        return DEFAULT_DURATION;
    }

    @Override
    public void animateView(View view) {
        PropertyValuesHolder mHolder = PropertyValuesHolder.ofFloat(getClass().getName(), getValue());
        setValueAnimatorProperty(mHolder);
        start();
    }

    protected abstract void onAnimationUpdate(ValueAnimator animation, View currentView);

}
