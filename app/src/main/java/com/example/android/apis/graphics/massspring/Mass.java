package com.example.android.apis.graphics.massspring;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Mass {
    private boolean canMove = true;

    private float m;
    private float x;
    private float y;
    private float xV;
    private float yV;

    private float xForce;
    private float yForce;

    public Mass(boolean canMove, float m, float x, float y) {
        this.canMove = canMove;
        this.m = m;
        this.x = x;
        this.y = y;
    }

    public void addFroce(float xForce, float yForce) {
        this.xForce += xForce;
        this.yForce += yForce;
    }

    private List<Spring> springs = new ArrayList<Spring>();

    public boolean add(Spring object) {
        return springs.add(object);
    }

    public void move(float t) {
        if (!canMove) {
            return;
        }

        for (Spring spring : springs) {
            xForce += spring.getxForce(this);
            //Log.d("MASS",xForce+"");
            yForce += spring.getyForce(this);
           //Log.d("MASS",yForce+"");
        }
        move(xForce, yForce, t);

        xForce = 0;
        yForce = 0;
    }

    private void move(float xForce, float yForce, float t) {
        float xa = xForce / m;
        float ya = yForce / m;

        x += (xV + 0.5f * xa * t )* t * 0.97f;
        y += (yV + 0.5f * ya * t )* t * 0.97f;
        xV += xa * t;
        xV *= 0.97f;
        yV += ya * t;
        yV *= 0.97f;
    }

    public float getM() {
        return m;
    }

    public void setM(float m) {
        this.m = m;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getxV() {
        return xV;
    }

    public void setxV(float xV) {
        this.xV = xV;
    }

    public float getyV() {
        return yV;
    }

    public void setyV(float yV) {
        this.yV = yV;
    }
}
