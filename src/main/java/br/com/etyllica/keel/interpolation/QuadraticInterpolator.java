package br.com.etyllica.keel.interpolation;

/**
 * Code based on https://www.mathcelebrity.com/3ptquad.php
 */
public class QuadraticInterpolator extends InterpolatorImpl {
	
	@Override
	public double interpolate(double x) {
				
		double[] coeficients = getCoefficients(x);
		
		double a = coeficients[0];
		double b = coeficients[1];
		double c = coeficients[2];
			
		return a*x*x + b*x + c;
	}
	
	public double[] getCoefficients(double x) {
		
		double b = points.get(0).getX();
		double a = b*b;
		double c = 1;
		double d = points.get(0).getY(); 
		
		double f = points.get(1).getX();
		double e = f*f;
		double g = 1;
		double h = points.get(1).getY();
		
		double j = points.get(2).getX();
		double i = j*j;
		double k = 1;
		double l = points.get(2).getY();
				
		double delta = (a * f * k) + (b * g * i) + (c * e * j) - (c * f * i) - (a * g * j) - (b * e * k);
		
		double aNumerator =  (d * f * k) + (b * g * l) + (c * h * j) - (c * f * l) - (d * g * j) - (b * h * k);
		
		double bNumerator = (a * h * k) + (d * g * i) + (c * e * l) - (c * h * i) - (a * g * l) - (d * e * k);
		
		double cNumerator = (a * f * l) + (b * h * i) + (d * e * j) - (d * f * i) - (a * h * j) - (b * e * l);
		
		double aCoef = aNumerator/delta;
		
		double bCoef = bNumerator/delta;
		
		double cCoef = cNumerator/delta;
		
		double[] coeficients = new double[3];
		
		coeficients[0] = aCoef;
		coeficients[1] = bCoef;
		coeficients[2] = cCoef;
		
		return coeficients;
	}
	
}
