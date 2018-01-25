package com.harium.keel.interpolation;

import com.harium.keel.util.CameraUtil;

public class TriangularSimilarityInterpolator implements Interpolator {

    private double inverse;
    private double focalLength;

    /**
     * Based on https://www.pyimagesearch.com/2015/01/19/find-distance-camera-objectmarker-using-python-opencv/
     * @param realWidth
     * @param realDistance
     * @param widthInPixels
     */
    public TriangularSimilarityInterpolator(double realWidth, double realDistance, int widthInPixels) {
        focalLength = CameraUtil.focalLength(realWidth, realDistance, widthInPixels);
        inverse = focalLength * realWidth;
    }

    @Override
    public double interpolate(double x) {
        return inverse / x;
    }
}
