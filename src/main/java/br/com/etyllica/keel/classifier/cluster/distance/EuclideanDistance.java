package br.com.etyllica.keel.classifier.cluster.distance;

import br.com.etyllica.core.linear.Point2D;

public class EuclideanDistance implements DistanceMeasure {

	public double distance(Point2D a, Point2D b) {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		
		return Math.sqrt(dx*dx + dy*dy);
	}

}
