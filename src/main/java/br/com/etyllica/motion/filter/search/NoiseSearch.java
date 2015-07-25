package br.com.etyllica.motion.filter.search;


import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.feature.Component;

public class NoiseSearch extends PolygonalSearch {

	private int radius = 20;

	private int minNeighboors = 0;
	private int maxNeighboors = Integer.MAX_VALUE;

	public NoiseSearch(int w, int h) {
		super(w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {
		// TODO Auto-generated method stub
		return filter(bimg, component).get(0);
	}	
	
	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {

		super.setup();

		Component poly = new Component(w, h);

		List<Point2D> points = component.getPoints();

		for(int i=0;i<points.size()-1;i++){

			Point2D point = points.get(i);

			int neighboors = 0;

			for(int j=i+1;j<points.size();j++){

				Point2D pointJ = points.get(j);

				if(insideCircle(point.getX(), point.getY(), radius, pointJ.getX(), pointJ.getY())){

					neighboors++;

					if(neighboors>=minNeighboors&&neighboors<maxNeighboors){

						polygon.addPoint((int)point.getX(), (int)point.getY());
						poly.add(point);
						break;
					}

				}

			}

		}

		result.add(poly);

		return result;
	}

	private boolean insideCircle(double cx, double cy, double radius, double px, double py){

		double difX = (px - cx)*(px - cx);
		double difY = (py - cy)*(py - cy);

		return difX + difY < radius*radius;

	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getMinNeighboors() {
		return minNeighboors;
	}

	public void setMinNeighboors(int minNeighboors) {
		this.minNeighboors = minNeighboors;
	}

	public int getMaxNeighboors() {
		return maxNeighboors;
	}

	public void setMaxNeighboors(int maxNeighboors) {
		this.maxNeighboors = maxNeighboors;
	}

}
