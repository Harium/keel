package br.com.etyllica.motion.filter.modifier.posit;

public class TObject {

	int nbPts;
	double[][] objectPts;/* Array of coordinates of points */
	double[][] objectVects;/* Array of coordinates of vectors from reference pt to all pts */
	double[][] objectCopy;/* Copy of objectVects, used because SVD code destroys input data */
	double[][] objectMatrix;/* Pseudoinverse of objectVects */
	
}
