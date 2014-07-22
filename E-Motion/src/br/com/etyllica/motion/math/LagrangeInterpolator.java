package br.com.etyllica.motion.math;


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
	            	
	                mult *= (v - points.get(j).getX()) / (points.get(i).getX() - points.get(j).getX());
	            }
	            
	            sum += mult * points.get(i).getY();
	        }
			
		}
		
		return sum;
	}	
	
}
