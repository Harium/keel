package br.com.etyllica.keel.classifier.cluster.distance;

import br.com.etyllica.core.linear.Point2D;

public interface DistanceMeasure {
	double distance(Point2D a, Point2D b);
}
