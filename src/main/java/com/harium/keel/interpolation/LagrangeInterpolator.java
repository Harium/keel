package com.harium.keel.interpolation;


public class LagrangeInterpolator extends InterpolatorImpl {
	
	@Override
	public double interpolate(double v) {
		
		int n = points.size();
		
		double mult = 0;
		
		double sum = 0;
		
		for (int i = 0; i < n ; i++) {
			mult = 1;
			
	        for (int j = 0; j < n ; j++) {
	            if (j != i) {
	                mult *= (v - points.get(j).x) / (points.get(i).x - points.get(j).x);
	            }
	            
	            sum += mult * points.get(i).y;
	        }
		}
		
		return sum;
	}	
	
}
