package br.com.etyllica.motion.classifier.cluster.distance;

import br.com.etyllica.core.linear.Point2D;

public interface DistanceMeasure {
	double distance(Point2D a, Point2D b);
}
