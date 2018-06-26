package com.example.android.apis.graphics.massspring;

public class Spring {
    private float length;
    private float r;

    public Spring(float length, float r, Mass m1, Mass m2) {
        this.length = length;
        this.r = r;
        this.m1 = m1;
        this.m2 = m2;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setM1(Mass m1) {
        this.m1 = m1;
    }

    public void setM2(Mass m2) {
        this.m2 = m2;
    }

    private float xForce = 0;
    private float yForce = 0;

    private Mass m1;
    private Mass m2;

    public void calculate() {
        float dy = Math.abs(m1.getY() - m2.getY());
        float dx = Math.abs(m1.getX() - m2.getX());
        float force = (length - (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))) * r;
        //float force = (length - (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))) * r * 0.95f;
        xForce = force * dx / length;
        yForce = force * dy / length;
    }

    public float getxForce(Mass mass) {
        if (mass == null) {
            return 0;
        }
        if (mass.equals(m1)) {
            return getxForce();
        } else if (mass.equals(m2)) {
            return -getxForce();
        }
        return 0;
    }

    private float getxForce() {
        if (m1.getX() - m2.getX() > 0) {
            return xForce;
        } else {
            return -xForce;
        }
    }

    public float getyForce(Mass mass) {
        if (mass == null) {
            return 0;
        }
        if (mass.equals(m1)) {
            return getyForce();
        } else if (mass.equals(m2)) {
            return -getyForce();
        }
        return 0;
    }

    private float getyForce() {
        if (m1.getY() - m2.getY() > 0) {
            return yForce;
        } else {
            return -yForce;
        }
    }
}
