package com.example.kanimationcontroller;

import android.animation.Animator;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kognsin on 9/24/2016.
 */

public class BaseAnimationControl {

    protected View mView;
    private static final String TAG = "BaseAnimationControl";
    protected List<IAnimateSet> animateSets = new ArrayList<>();

    public BaseAnimationControl(View view) {
        this.mView = view;
    }

    public List<IAnimateSet> getAnimateSets() {
        return animateSets;
    }

    public boolean isGone(){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof Gone) return true;
        }
        return false;
    }

    public int getHeight(){
       return isGone() ? 0 : mView.getHeight();
    }

    public int getWidth(){
        return isGone() ? 0 : mView.getWidth();
    }

    public float getScaledHeight(){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof ScaleY){
                return (getHeight() * animateSet.getValue());
            }
        }
        return getHeight();
    }

    public BaseAnimationControl newAnimate(){
        mView.animate().setDuration(250);
        animateSets.clear();
        return this;
    }

    public BaseAnimationControl setDuration(int duration){
        mView.animate().setDuration(duration);
        return this;
    }

    public float getScaledWidth(){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof ScaleX){
                return (getWidth() * animateSet.getValue());
            }
        }
        return getWidth();
    }

    public float getX(){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof X || animateSets instanceof TransX){
                float x = animateSet.getValue();
                if (getWidth() > getScaledWidth()){
                    x += ((getWidth() - getScaledWidth()) / 2);
                } else {
                    x -= ((getScaledWidth() - getWidth()) / 2);
                }
                return x;
            }
        }
        return mView.getX();
    }

    public float getY(){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof Y || animateSet instanceof TransY){
                float y = (animateSet).getValue();
                if (getHeight() > getScaledHeight()){
                    y += ((getHeight() - getScaledHeight()) / 2);
                } else {
                    y -= ((getScaledHeight() - getHeight()) / 2);
                }
                return y;
            }
        }
        return mView.getY();
    }

    public BaseAnimationControl start(){
        start(null);
        return this;
    }

    public BaseAnimationControl startDelay(int millisec){
        startDelay(millisec, null);
        return this;
    }

    public BaseAnimationControl start(Animator.AnimatorListener listener){
        for (IAnimateSet animateSet : animateSets) {
            animateSet.animateView(mView);
        }
        mView.animate().setInterpolator(new FastOutSlowInInterpolator()).setListener(listener).start();
        return this;
    }

    public View getView() {
        return mView;
    }

    public BaseAnimationControl startDelay(int milliseconds, final Animator.AnimatorListener listener){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start(listener);
            }
        }, milliseconds);
        return this;
    }

    public BaseAnimationControl gone(){
        animateSets.add(new Gone());
        return this;
    }

    public BaseAnimationControl reset(){
        animateSets.add(new Reset());
        return this;
    }

    public BaseAnimationControl flip(int decree){
        animateSets.add(new RotationY(decree));
        return this;
    }

    public BaseAnimationControl goToLeft(ViewGroup rootView){
        animateSets.add(new X(rootView.getLeft()));
        return this;
    }

    public BaseAnimationControl goToRight(ViewGroup rootView){
        float screenW = rootView.getRight();
        float destVal = screenW - getScaledWidth();
        animateSets.add(new X(destVal));
        return this;
    }

    public BaseAnimationControl goToTop(ViewGroup rootView){
        float top = rootView.getTop();
        float scale = (getScaledHeight() - getHeight());
        animateSets.add(new Y(top + (scale / 2)));
        return this;
    }

    public BaseAnimationControl goToBottom(ViewGroup rootView){
        float screenHeight = rootView.getBottom();
        float destVal = screenHeight - (getHeight() + ((getScaledHeight() - getHeight()) / 2));
        animateSets.add(new Y(destVal));
        return this;
    }

    public BaseAnimationControl moveToCenterHorizontal(ViewGroup rootView){
        float screenW = rootView.getWidth();
        float destVal = (screenW / 2) - (getWidth() / 2);
        animateSets.add(new X(destVal));
        return this;
    }

    public BaseAnimationControl moveToCenterVertical(ViewGroup rootView){
        float screenH = rootView.getHeight();
        float destVal = (screenH / 2) - (getHeight() / 2);
        animateSets.add(new Y(destVal));
        return this;
    }

    public BaseAnimationControl stackToTopOf(BaseAnimationControl _baseAnimationControl){
        float value;
        if (_baseAnimationControl instanceof ImageAnimationControl){
            value = _baseAnimationControl.getY() - _baseAnimationControl.getScaledHeight();
            if (_baseAnimationControl.getHeight() > _baseAnimationControl.getScaledHeight()){
                value += ((_baseAnimationControl.getHeight() - _baseAnimationControl.getScaledHeight()) / 2);
            } else {
                value -=((_baseAnimationControl.getHeight() - _baseAnimationControl.getScaledHeight()) / 2);
            }
        } else {
            value = _baseAnimationControl.getY() - _baseAnimationControl.getHeight();
        }
        if (getHeight() > getScaledHeight()){
            value += ((getHeight() - getScaledHeight())/2);
        } else {
            value -= ((getScaledHeight() - getHeight())/2);
        }
        animateSets.add(new Y(value));
        return this;
    }

    public BaseAnimationControl stackToBottomOf(BaseAnimationControl _baseAnimationControl){
        float value;
        if (_baseAnimationControl instanceof ImageAnimationControl){
            value = _baseAnimationControl.getY() + _baseAnimationControl.getScaledHeight();
            if (_baseAnimationControl.getHeight() > _baseAnimationControl.getScaledHeight()){
                value -= ((_baseAnimationControl.getHeight() - _baseAnimationControl.getScaledHeight()) / 2);
            } else {
                value +=((_baseAnimationControl.getHeight() - _baseAnimationControl.getScaledHeight()) / 2);
            }
        } else {
            value = _baseAnimationControl.getY() + _baseAnimationControl.getScaledHeight();
        }
        if (getHeight() > getScaledHeight()){
            value -= ((getHeight() - getScaledHeight())/2);
        } else {
            value += ((getScaledHeight() - getHeight())/2);
        }
        animateSets.add(new Y(value));
        return this;
    }

    public BaseAnimationControl stackToRightOf(BaseAnimationControl _baseAnimationControl){
        float value;
        if (_baseAnimationControl instanceof ImageAnimationControl){
            value = _baseAnimationControl.getX() + _baseAnimationControl.getScaledWidth();
        } else {
            value = _baseAnimationControl.getX() + _baseAnimationControl.getScaledWidth();
        }
        if (getWidth() > getScaledWidth()){
            value -= ((getWidth() - getScaledWidth())/2);
        } else {
            value += ((getScaledWidth() - getWidth())/2);
        }
        animateSets.add(new X(value));
        return this;
    }

    public BaseAnimationControl stackToLeftOf(BaseAnimationControl _baseAnimationControl){
        float value;
        if (_baseAnimationControl instanceof ImageAnimationControl){
            value = _baseAnimationControl.getX() - _baseAnimationControl.getScaledWidth();
        } else {
            value = _baseAnimationControl.getX() - _baseAnimationControl.getWidth();
        }
        if (getWidth() > getScaledWidth()){
            value += ((getWidth() - getScaledWidth())/2);
        } else {
            value -= ((getScaledWidth() - getWidth())/2);
        }
        animateSets.add(new X(value));
        return this;
    }

    public BaseAnimationControl space(){

        return this;
    }

    public BaseAnimationControl y(float value){
        animateSets.add(new Y(value - ((getHeight() - getScaledHeight())/2)));
        return this;
    }

    public BaseAnimationControl x(float value){
        animateSets.add(new X(value - ((getWidth() - getScaledWidth())/2)));
        return this;
    }

    public BaseAnimationControl marginLeft(float value){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof X){
                animateSet.setValue(animateSet.getValue() + value);
            }
        }
        return this;
    }

    public BaseAnimationControl marginRight(float value){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof X){
                animateSet.setValue(animateSet.getValue() - value);
            }
        }
        return this;
    }

    public BaseAnimationControl marginTop(float value){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof Y){
                animateSet.setValue(animateSet.getValue() + value);
            }
        }
        return this;
    }

    public BaseAnimationControl marginBottom(float value){
        for (IAnimateSet animateSet : animateSets) {
            if (animateSet instanceof Y){
                animateSet.setValue(animateSet.getValue() - value);
            }
        }
        return this;
    }

    public BaseAnimationControl scaleX(float value){
        animateSets.add(new ScaleX(value));
        return this;
    }

    public BaseAnimationControl scaleY(float value){
        animateSets.add(new ScaleY(value));
        return this;
    }

    public BaseAnimationControl translationX(float value){
        animateSets.add(new TransX(value));
        return this;
    }

    public BaseAnimationControl translationY(float value){
        animateSets.add(new TransY(value));
        return this;
    }

    public BaseAnimationControl size(float w, float h, int duration){
        animateSets.add(new Size(mView, duration, w, h));
        return this;
    }

}
