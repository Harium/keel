package com.harium.keel.interpolation;

public class PowerInterpolator implements Interpolator {

    private double power = 1;
    private double b = 0;

    public PowerInterpolator(double power, double coefficient) {
        this.power = power;
        this.b = coefficient;
    }

    @Override
    public double interpolate(double x) {
        return b * Math.pow(x, power);
    }
}
