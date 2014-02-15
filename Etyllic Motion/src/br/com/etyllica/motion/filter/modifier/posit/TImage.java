package br.com.etyllica.motion.filter.modifier.posit;

public class TImage {

	int nbPts;
	int [] imageCenter = new int[2];
	int [][] imagePts;
	double[][] imageVects;/* scaled orthographic projections */
	double[][] oldImageVects;/* projections at previous iteration step */
	double[] epsilon;/* Corrections to scaled orthographic projections at each iteration */
	
}
