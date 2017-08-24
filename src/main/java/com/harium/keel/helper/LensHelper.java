package com.harium.keel.helper;

public class LensHelper {

	public static double[] calculateSphericalDistortion(int x, int y, double distortionConstant) {
				
		int square = x*x+y*y;
		
		double[] coordinates = new double[2];
		
		//Actual x coordinate
		coordinates[0] = x*(1+distortionConstant*square);
		
		//Actual y coordinate
		coordinates[0] = y*(1+distortionConstant*square);
		
		return coordinates;
		
	}
	
}
