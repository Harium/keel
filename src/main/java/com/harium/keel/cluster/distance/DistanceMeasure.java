package com.harium.keel.cluster.distance;

import com.harium.etyl.geometry.Point2D;

public interface DistanceMeasure {
    double distance(Point2D a, Point2D b);
}
