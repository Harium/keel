package br.com.etyllica.keel.interpolation;

import com.harium.etyl.linear.Point2D;

import java.util.ArrayList;
import java.util.List;

public abstract class InterpolatorImpl implements Interpolator {

	protected List<Point2D> points = new ArrayList<Point2D>();
	
	public InterpolatorImpl() {
		super();
	}
	
	public void addPoint(double x, double y) {
		points.add(new Point2D(x, y));
	}
	
	public void addPoint(Point2D point) {
		points.add(point);
	}
	
	public void reset() {
		points.clear();
	}
	
}
