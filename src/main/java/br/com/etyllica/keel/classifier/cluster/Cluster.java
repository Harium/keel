package br.com.etyllica.keel.classifier.cluster;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;

public class Cluster {
	
	public static final int UNDEFINED_CLUSTER = -1;
	
	public List<Point2D> points;
	public Point2D centroid;
	public int id;
		
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<Point2D>();
		this.centroid = null;
	}

	public List<Point2D> getPoints() {
		return points;
	}
	
	public void addPoint(Point2D point) {
		points.add(point);
	}

	public void setPoints(List<Point2D> points) {
		this.points = points;
	}

	public Point2D getCentroid() {
		return centroid;
	}

	public void setCentroid(Point2D centroid) {
		this.centroid = centroid;
	}
	
	public void calculateCentroid() {
		double sumX = 0, sumY = 0;
		for (Point2D point : points) {
			sumX += point.getX();
			sumY += point.getY();
		}
		
		centroid = new Point2D(sumX/points.size(), sumY/points.size());
	}

	public int getId() {
		return id;
	}
	
	public void clear() {
		points.clear();
	}
}
