package br.com.etyllica.motion.custom.polygon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.features.Component;

public class NoiseQuickHullFilter extends QuickHullFilter{
	
	private int radius = 20;

	public NoiseQuickHullFilter(int w, int h) {
		super(w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {

		polygon.reset();
		
		List<Component> result = new ArrayList<Component>();
		
		Component poly = new Component(w, h);
		
		List<Ponto2D> points = component.getPoints();
		
		List<Ponto2D> cleanPoints = new ArrayList<Ponto2D>();
		
		for(int i=0;i<points.size()-1;i++){
			
			Ponto2D point = points.get(i);
			
			for(int j=i+1;j<points.size();j++){
				
				Ponto2D pointJ = points.get(j);
				
				if(insideCircle(point.getX(), point.getY(), radius, pointJ.getX(), pointJ.getY())){
					cleanPoints.add(pointJ);
					break;
				}
				
			}
			
		}		
				
		for(Ponto2D ponto: quickHull(cleanPoints)){
						
			polygon.addPoint((int)ponto.getX(), (int)ponto.getY());
			poly.add(ponto);
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
		
}
