package br.com.etyllica.motion.filter.modifier.posit;

/*****************************************************************************/

/* 

Code for computing object pose using POSIT 

Inputs: 1) a file containing number of points and coordinates of object points;
        2) a file containing integer coordinates of image points;
Important: Image point at position i in image file should correspond to object point at 
position i in object file

Outputs: 1) Rotation of scene with respect to camera
         2) Translation vector from projection center of camera
	    to FIRST point of scene (object) file.
            
Reference: D. DeMenthon and L.S. Davis, "Model-Based Object Pose in 25 Lines of Code", 
International Journal of Computer Vision, 15, pp. 123-141, June 1995. 

Adapted to Java from C code at http://www.cfar.umd.edu/~daniel/POSIT.zip 

*/

/*****************************************************************************/

public class PositModifier {

	private static final int maxCount = 30;/* exit iteration with a message after this many loops */
	private static final int nbObjectCoords = 3; /* x, y, z */
	private static final int nbImageCoords = 2; /* x, y */

	/*
	POS function 
	(Pose from Orthography and Scaling, a scaled orthographic proj. approximation).
	Returns one translation and one rotation.
	 */	
	void POS(TObject object, TImage image, TCamera camera) {

		double[] I0 = new double[3];
		double[] J0 = new double[3];
		double[] row1 = new double[3];
		double[] row2 = new double[3];
		double[]  row3 = new double[3];

		double    I0I0, J0J0;
		int  i, j;
		double scale, scale1, scale2;


		/*Computing I0 and J0, the vectors I and J in TRs listed above */
		for (i=0;i<nbObjectCoords;i++){
			I0[i]=0;
			J0[i]=0;
			for (j=0;j<object.nbPts;j++){
				I0[i]+=object.objectMatrix[i][j]*image.imageVects[j][0];
				J0[i]+=object.objectMatrix[i][j]*image.imageVects[j][1];
			}
		}

		I0I0=I0[0]*I0[0] + I0[1]*I0[1] + I0[2]*I0[2];
		J0J0=J0[0]*J0[0] + J0[1]*J0[1] + J0[2]*J0[2];

		scale1 = Math.sqrt(I0I0);
		scale2 = Math.sqrt(J0J0);
		scale = (scale1 + scale2) / 2.0;

		/*Computing TRANSLATION */
		camera.translation[0] = image.imagePts[0][0]/scale;
		camera.translation[1] = image.imagePts[0][1]/scale;
		camera.translation[2] = camera.focalLength/scale;

		/* Computing ROTATION */
		for (i=0;i<3;i++){
			row1[i]=I0[i]/scale1;
			row2[i]=J0[i]/scale2;
		}

		row3[0]=row1[1]*row2[2]-row1[2]*row2[1];/* Cross-product to obtain third row */
		row3[1]=row1[2]*row2[0]-row1[0]*row2[2];
		row3[2]=row1[0]*row2[1]-row1[1]*row2[0];

		for (i=0;i<3;i++){

			camera.rotation[0][i]=row1[i];
			camera.rotation[1][i]=row2[i];
			camera.rotation[2][i]=row3[i];

		}
	}

	/* 
	Iterate over results obtained by the POS function;
	see paper "Model-Based Object Pose in 25 Lines of Code", IJCV 15, pp. 123-141, 1995.
	 */
	void POSIT(TObject object, TImage image, TCamera camera) {

		int i, j, iCount;
		boolean converged = false;
		long imageDiff = 0;

		/* Starting point for iteration loop */
		for(iCount=0;iCount<maxCount;iCount++){
			if(iCount==0){
				for (i=0;i<image.nbPts;i++){
					for(j=0;j<nbImageCoords;j++){
						image.imageVects[i][j]=(double)(image.imagePts[i][j]-image.imagePts[0][j]);
					}
				}
			}
			else{/* iCount>0 */
				/* Compute new imageVects */
				for (i=0;i<image.nbPts;i++){
					image.epsilon[i] = 0.0;
					for (j=0;j<3;j++){
						image.epsilon[i] += object.objectVects[i][j] * camera.rotation[2][j]; /*dot product M0Mi.k*/
					}
					image.epsilon[i] /= camera.translation[2]; /* divide by Z0 */
				}
				/* Corrected image vectors */	
				for (i=0;i<image.nbPts;i++){
					for(j=0;j<nbImageCoords;j++){
						image.imageVects[i][j]=(double)image.imagePts[i][j]*(1+image.epsilon[i])-image.imagePts[0][j];
					}
				}

				imageDiff = GetImageDifference(image);/*using pts gives same result */
				//printf("imageDiff %ld\n",imageDiff);
			}

			/* Remember old imageVects */
			for (i=0;i<image.nbPts;i++){
				image.oldImageVects[i][0]=image.imageVects[i][0];
				image.oldImageVects[i][1]=image.imageVects[i][1];
			}

			/* Compute rotation and translation */
			POS(object,image, camera);

			converged = (iCount>0 && imageDiff==0);

			if(converged) break;

			if(iCount==maxCount && !converged){
				//printf("POSIT did not converge \n");
			}
		}/*end for*/;
	}

	/*
	Get sum of differences between coordinates in lists
	of old image points and new image points
	 */
	private long GetImageDifference(TImage image) {
		
		int i, j;
		long sumOfDiffs = 0;

		for (i=0;i<image.nbPts;i++){
			for (j=0;j<2;j++){
				sumOfDiffs += Math.abs(Math.floor(0.5+image.imageVects[i][j])-Math.floor(0.5+image.oldImageVects[i][j]));
			}
		}
		return sumOfDiffs;
	}

}
