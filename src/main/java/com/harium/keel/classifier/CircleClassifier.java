package com.harium.keel.classifier;

import com.harium.etyl.geometry.Point2D;
import com.harium.keel.feature.PointFeature;

public class CircleClassifier implements Classifier<PointFeature, Boolean> {

    private int step = 2;
    private int iterations = 4;
    private double radiusTolerance = 0.1;

    public CircleClassifier() {
    }

    public CircleClassifier(int iterations) {
        this.iterations = iterations;
    }

    public Boolean classify(PointFeature feature) {

        float difRadius;
        int dimension;

        if (feature.getW() > feature.getH()) {
            difRadius = feature.getW() / (float) feature.getH();
            dimension = feature.getW();
        } else {
            difRadius = feature.getH() / (float) feature.getW();
            dimension = feature.getH();
        }

        int radius = dimension / 2;

        //If component is not circular with tolerance = radiusTolerance
        if (difRadius > 1 + radiusTolerance) {
            return false;
        }

        Point2D center = feature.getCenter();
        double cx = center.getX();
        double cy = center.getY();

        double interval = 180 / iterations;

        for (int i = 0; i < iterations; i++) {

            double angle = interval * i;

            double dxi = Math.cos(angle);
            double dyi = Math.sin(angle);

            for (int r = 0; r < radius; r += step) {
                double dx = r * dxi;
                double dy = r * dyi;

                if (!feature.colidePoint((int) (cx + dx), (int) (cy + dy))) {
                    return false;
                }
                if (!feature.colidePoint((int) (cx - dx), (int) (cy - dy))) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getRadiusTolerance() {
        return radiusTolerance;
    }

    public void setRadiusTolerance(double radiusTolerance) {
        this.radiusTolerance = radiusTolerance;
    }
}
