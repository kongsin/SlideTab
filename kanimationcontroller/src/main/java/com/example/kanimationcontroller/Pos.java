package com.example.kanimationcontroller;

import android.graphics.Rect;

/**
 * Created by kognsin on 9/24/2016.
 */

public class Pos {

    private float l, t, r, b;
    private float w, h;

    public Pos(Rect rect) {
        setL(rect.left);
        setB(rect.bottom);
        setR(rect.right);
        setT(rect.top);
        setW(rect.width());
        setH(rect.height());
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getL() {
        return l;
    }

    public void setL(float l) {
        this.l = l;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getT() {
        return t;
    }

    public void setT(float t) {
        this.t = t;
    }

    public void setH(float h) {
        this.h = h;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public float getW() {
        return w;
    }
}
