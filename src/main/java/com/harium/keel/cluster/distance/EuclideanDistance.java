package com.harium.keel.cluster.distance;

import com.harium.etyl.geometry.Point2D;

public class EuclideanDistance implements DistanceMeasure {

	public double distance(Point2D a, Point2D b) {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		
		return Math.sqrt(dx*dx + dy*dy);
	}

}
