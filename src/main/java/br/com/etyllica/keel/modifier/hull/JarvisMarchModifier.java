package br.com.etyllica.keel.modifier.hull;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.keel.feature.Component;

/**
 * This class provides a method to get the convex hull from a set of
 * points. The method implements the Jarvis March (Giftwrapping in 2D)
 * algorithm.
 * <br>
 * (You can find the algorithm in pseudocode at 
 * http://en.wikipedia.org/wiki/Gift_wrapping_algorithm)
 * @author David Hägele
 *
 */
public class JarvisMarchModifier implements HullModifier<List<Point2D>> {

	@Override
	public Component modifyComponent(Component component) {
				
		List<Point2D> convexPolygon = modify(component);
		
		Component poly = new Component(0, 0);
		
		poly.setPoints(convexPolygon);
		
		return poly;
		
	}
	
	/**
	 * calculates the convex hull of the specified array of points.
	 * <br>
	 * the array of points has to be of dimensions [n][2], <br>
	 * which means that a point can be obtained like this: <br>
	 * <code> double[] point = array[i]; </code><br>
	 * and coordinates like this: <br>
	 * <code> x= array[i][0] and y= array[i][1] </code>
	 * @param points in double[][]
	 * @return double[][] with points of the convex hull
	 */
	public List<Point2D> modify(Component component) {
		
		List<Point2D> points = component.getPoints();
		
		if(points.size() < 4){
			return points;
		}
		
		Point2D pointOnHull = points.get(getIndexOfLeftMostPoint(points)); // leftmost point in shape
		
		List<Point2D> hull = new ArrayList<Point2D>();
		
		int i = 0;
		
		Point2D endpoint = points.get(0); // initial endpoint for a candidate edge on the hull
		
		do {
			
			hull.add(pointOnHull);
			
			endpoint = points.get(0);
			
			for(int j = 1; j < points.size(); j++){
				if(endpoint == pointOnHull || isLeftOfLine(points.get(j), hull.get(i), endpoint)){
					endpoint = points.get(j); // found greater left turn, update endpoint
				}
			}
			i++;
			pointOnHull = endpoint;
			
		} while(endpoint != hull.get(0));
		
		/* i is now equal to the number of points of the hull.
		 * need to make correctly sized hull array now.
		 */
		List<Point2D> hullToReturn = new ArrayList<Point2D>();
		
		for(int k = 0; k < i; k++){
			hullToReturn.add(hull.get(k));
		}
		
		return hullToReturn;
	}
	
	
	static int getIndexOfLeftMostPoint(List<Point2D> points){
		
		int index = 0;
		
		double x = points.get(0).getX();
		
		for(int i = 1; i < points.size(); i++){
			if(points.get(i).getX() < x){
				x = points.get(i).getX();
				index = i;
			}
		}
		return index;
	}
	
	
	static boolean isLeftOfLine(Point2D point, Point2D linePoint1, Point2D linePoint2){
		// vec1 = vector from linePoint1 to linePoint2
		double[] vec1 = new double[]{ 	linePoint2.getX() - linePoint1.getX(),
										linePoint2.getY() - linePoint1.getY()	};
		// vec2 = vector from linePoint1 to point
		double[] vec2 = new double[]{	point.getX() - linePoint1.getX(),
										point.getY() - linePoint1.getY()	};
		
		// making vec1 a unit vector
		double x = vec1[0];
		vec1[0] = vec1[0] / sqrt(x*x + vec1[1] * vec1[1]);
		vec1[1] = vec1[1] / sqrt(x*x + vec1[1] * vec1[1]);
		
		/* cause vec1 is now a unit vector (length = 1 = hypotenuse), sine 
		 * and cosine of vec1's angle can be obtained as follows: 
		 */
		double cos = vec1[0];
		double sin = vec1[1];
		
		/* making rotation matrix to turn coordinate system.
		 * (turn vectors clockwise)
		 * 
		 * clockwise rotation matrix  is  inverted counterclckws rot. matrix
		 *    counterclockwise rot. matrix:
		 *           cos(a)  -sin(a)
		 *           sin(a)   cos(a)
		 * 
		 *    clockwise rot. matrix:
		 *       cos(a)/det  sin(a)/det
		 *      -sin(a)/det  cos(a)/det
		 * 
		 * (det = determinant of counterclockwise rot. matrix)
		 */ 
		double det = (cos * cos) + (sin * sin);
		
		// rotating vec2 -> matrix * vec2
		x = vec2[0];
		vec2[0] =  cos/det * x	+ sin/det * vec2[1];
		vec2[1] = -sin/det * x 	+ cos/det * vec2[1];
		
		/* now the line between linePoint1 and 2 is x-Axis.
		 * if vec2 (vector from origin to point) points upwards,
		 * then point is left of line */
		return vec2[1] > 0.0;
	}

}