package com.harium.keel.core.gesture;

import com.harium.etyl.linear.Point2D;

import java.util.List;

public class PolygonMatcher {

    private int minDistance = 0;

    public String toRegExp(List<Point2D> points) {

        StringBuilder builder = new StringBuilder();

        double ox = points.get(0).getX();
        double oy = points.get(0).getY();

        for (int i = 1; i < points.size(); i++) {

            double px = points.get(i).getX();
            double py = points.get(i).getY();

            builder.append(getExp(ox, oy, px, py));

            ox = px;
            oy = py;
        }

        return builder.toString();

    }

    private String getExp(double ox, double oy, double px, double py) {

        Point2D p = new Point2D(ox, oy);

        if (p.distance(px, py) < minDistance) {
            return "";
        }

        if (ox <= px) {

            if (oy >= py) {
                return "B";
            } else if (oy < py) {
                return "D";
            }

        } else if (ox > px) {

            if (oy >= py) {
                return "A";
            } else if (oy < py) {
                return "C";
            }

        }

        return "";

    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

}
