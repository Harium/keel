package br.com.etyllica.motion.filter.modifier.posit;

import java.util.List;

import br.com.etyllica.linear.Point2D;

public class TImage {

	private List<Point2D> points;
	
	private Point2D imageCenter;
	
	int nbPts;
		
	int [][] imagePts;
	double[][] imageVects;/* scaled orthographic projections */
	double[][] oldImageVects;/* projections at previous iteration step */
	private double[] epsilon;/* Corrections to scaled orthographic projections at each iteration */
	
	public TImage(List<Point2D> points){
		this(points, 0, 0);
	}
	
	public TImage(List<Point2D> points, int x, int y){
		super();
		
		imageCenter = new Point2D(x, y);
		
		this.points = points;
		
		this.nbPts = points.size();
		
		imagePts = new int[nbPts][PositModifier.nbImageCoords];
		
		imageVects = new double[nbPts][PositModifier.nbImageCoords];
		
		oldImageVects = new double[nbPts][PositModifier.nbImageCoords];
		
		epsilon = new double[nbPts];
		
		int i = 0;
		
		for(Point2D point: points){
			
			imagePts[i][0] = (int)(point.getX()-imageCenter.getX());
			imagePts[i][1] = (int)(point.getY()-imageCenter.getY());
		
			i++;
		}
		
	}
	
	public double[] getEpsilon(){
		return epsilon;				
	}
	
	public void setEpsilon(int index, double value){
		this.epsilon[index] = value;
	}
	
	public void offsetEpsilon(int index, double offset){
		this.epsilon[index] += offset;
	}
	
		
}
