package br.com.etyllica.keel.classifier;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.keel.feature.Component;

public class CircleClassifier implements Classifier<Component, Boolean> {

	private int step = 2;
    private int iterations = 4;
    private double radiusTolerance = 0.05;

    public CircleClassifier() {
    }

    public CircleClassifier(int iterations) {
        this.iterations = iterations;
    }

    public Boolean classify(Component feature) {
        int difRadius = feature.getW() - feature.getH();
        if (difRadius < 0) {
            difRadius = -difRadius;
        }

        int dimension = Math.max(feature.getW(), feature.getH());
        int radius = dimension / 2;

        //If component is not circular with tolerance = radiusTolerance
        if ((difRadius > 0) && (radius / difRadius > radius * radiusTolerance)) {
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

            for (int r = 0; r < radius; r+= step) {
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
}
