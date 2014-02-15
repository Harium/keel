package br.com.etyllica.motion.filter.modifier.posit;

import java.util.List;

import br.com.etyllica.linear.Point3D;

public class TObject {

	private List<Point3D> points;
		
	int nbPts;
	double[][] objectPts;/* Array of coordinates of points */
	double[][] objectVects;/* Array of coordinates of vectors from reference pt to all pts */
	double[][] objectCopy;/* Copy of objectVects, used because SVD code destroys input data */
	
	public TObject(List<Point3D> points){
		super();
		
		this.points = points;
		
		nbPts = points.size();
		
		objectPts = new double[nbPts][PositModifier.nbObjectCoords];
		objectVects = new double[nbPts][PositModifier.nbObjectCoords];
		objectCopy = new double[nbPts][PositModifier.nbObjectCoords];
			
		int i = 0;
		
		for(Point3D point: points){
			
			objectPts[i][0] = point.getX();
			objectPts[i][1] = point.getY();
			objectPts[i][2] = point.getZ();
			
			objectCopy[i][0] = point.getX();
			objectCopy[i][1] = point.getX();
			objectCopy[i][2] = point.getX();
		
			i++;
		}
		
	}

	public double[][] getObjectPts() {
		return objectPts;
	}

	public double[][] getObjectVects() {
		return objectVects;
	}

	public double[][] getObjectCopy() {
		return objectCopy;
	}
		
}
