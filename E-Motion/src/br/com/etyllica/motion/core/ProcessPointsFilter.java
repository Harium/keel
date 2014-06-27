package br.com.etyllica.motion.core;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.linear.Point2D;

public interface ProcessPointsFilter<T> {

	public T process(BufferedImage buffer, List<Point2D> points);

}
