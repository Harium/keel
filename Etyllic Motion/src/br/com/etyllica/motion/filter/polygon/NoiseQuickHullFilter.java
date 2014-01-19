package br.com.etyllica.motion.filter.polygon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.features.Component;

public class NoiseQuickHullFilter extends QuickHullFilter{
	
	private int radius = 20;
	
	private int minNeighboors = 0;
	
	private int maxNeighboors = Integer.MAX_VALUE;

	public NoiseQuickHullFilter(int w, int h) {
		super(w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {

		super.setup();
		
		Component poly = new Component(w, h);
		
		List<Point2D> points = component.getPoints();
		
		List<Point2D> cleanPoints = new ArrayList<Point2D>();
		
		for(int i=0;i<points.size()-1;i++){
			
			Point2D point = points.get(i);
			
			int neighboors = 0;
			
			for(int j=i+1;j<points.size();j++){
				
				Point2D pointJ = points.get(j);
				
				if(insideCircle(point.getX(), point.getY(), pointJ.getX(), pointJ.getY())){
					
					neighboors++;
					
					if(neighboors>=minNeighboors&&neighboors<maxNeighboors){
						cleanPoints.add(pointJ);
						break;
					}
						
				}
				
			}
			
		}		
				
		for(Point2D ponto: quickHull(cleanPoints)){
						
			polygon.addPoint((int)ponto.getX(), (int)ponto.getY());
			poly.add(ponto);
		}
		
		result.add(poly);

		return result;
	}
	
	private boolean insideCircle(double cx, double cy, double px, double py){

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
