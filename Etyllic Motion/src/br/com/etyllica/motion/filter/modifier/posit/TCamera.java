package br.com.etyllica.motion.filter.modifier.posit;

public class TCamera {

	int focalLength;
	double[][] rotation = new double[3][3];/* Rotation of SCENE in camera reference, NOT other way around */
	double[] translation = new double[3];/* Translation of SCENE in camera reference */
	
}
