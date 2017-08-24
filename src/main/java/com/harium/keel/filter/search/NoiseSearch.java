package com.harium.keel.filter.search;


import java.util.List;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;
import com.harium.etyl.linear.Point2D;

public class NoiseSearch extends PolygonalSearch {

	private int radius = 20;

	private int minNeighboors = 0;
	private int maxNeighboors = Integer.MAX_VALUE;

	public NoiseSearch(int w, int h) {
		super(w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component filterFirst(ImageSource bimg, Component component) {
		// TODO Auto-generated method stub
		return filter(bimg, component).get(0);
	}	
	
	@Override
	public List<Component> filter(ImageSource bimg, Component component) {
		super.setup(component.getW(), component.getH());

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
