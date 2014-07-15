package br.com.etyllica.motion.modifier.hull;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.helper.PointListHelper;


/**
 * QuickHull modifier based on Alexander Hristov's code at
 * 
 * www.ahristov.com/tutorial/geometry-games/convex-hull.html
 *
 * @license GPL
 *
 */

public class QuickHullModifier implements HullModifier {
	
	public QuickHullModifier() {
		super();
	}

	@Override
	public Component modifyComponent(Component component) {
		
		List<Point2D> convexPolygon = modify(component);
		
		Component polygon = new Component(0, 0);
		
		for(Point2D ponto: convexPolygon) {
			polygon.add(ponto);
		}
		
		return polygon;
		
	}

	public List<Point2D> modify(Component component) {

		List<Point2D> points = component.getPoints();
		
		List<Point2D> list = PointListHelper.cloneList(points);
		
		if (list.size() < 3) return list;
		
		List<Point2D> convexHull = new ArrayList<Point2D>();

		Point2D firstPoint = list.get(0);
		
		Point2D minPoint = firstPoint;
		Point2D maxPoint = firstPoint;
		
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		
		// find extremals (in x axis)
		for (Point2D point: list) {
			
			if (point.getX() < minX) {
				minX = point.getX();
				minPoint = point;			
			}else if (point.getX() > maxX) {
				maxX = point.getX();
				maxPoint = point;       
			}
			
		}

		convexHull.add(minPoint);
		convexHull.add(maxPoint);
		
		list.remove(minPoint);
		list.remove(maxPoint);

		return calculateConvexHull(minPoint, maxPoint, list, convexHull);
		
	}
	
	private List<Point2D> calculateConvexHull(Point2D minPoint, Point2D maxPoint, List<Point2D> list, List<Point2D> hull) {
		
		List<Point2D> leftSet = new ArrayList<Point2D>();

		//Separate Points in Left or Right based on max/min point distance
		for (int i=list.size()-1; i > 0; i--) {
			
			Point2D p = list.get(i);
			
			if (!isPointLocationPositive(minPoint, maxPoint, p)) {
				leftSet.add(p);
				list.remove(i);
			}
			
		}
		
		List<Point2D> rightSet = list;
		
		hullSet(minPoint, maxPoint, rightSet, hull);
		
		hullSet(maxPoint, minPoint, leftSet, hull);

		return hull;
		
	}

	public void hullSet(Point2D a, Point2D b, List<Point2D> list, List<Point2D> hull) {
		
		if (list.size() == 0) return;
		
		int insertPosition = hull.indexOf(b);
		
		if (list.size() == 1) {
			
			Point2D p = list.get(0);
			list.remove(p);
			hull.add(insertPosition, p);
			return;
			
		}
				
		Point2D point = null;
		
		double dist = Double.MIN_VALUE;
		
		for (Point2D p : list) {
			
			double distance = distance(a,b,p);
			
			if (distance > dist) {
				dist = distance;
				point = p;
			}
		}
				
		list.remove(point);
		
		hull.add(insertPosition,point);

		// Determine who's to the left of AP segment
		List<Point2D> leftSetAP = new ArrayList<Point2D>();
		
		for (Point2D M: list) {
			
			if (isPointLocationPositive(a,point,M)) {

				leftSetAP.add(M);
				
			}
			
		}

		// Determine who's to the left of PB segment
		List<Point2D> leftSetPB = new ArrayList<Point2D>();
		
		for (Point2D M: list) {
			
			if (isPointLocationPositive(point,b,M)) {
			
				leftSetPB.add(M);
				
			}
			
		}
		
		hullSet(a, point, leftSetAP, hull);
		
		hullSet(point, b, leftSetPB, hull);
	}

	public boolean isPointLocationPositive(Point2D a, Point2D b, Point2D c) {
		
		double ABx = b.getX()-a.getX();
		double ABy = b.getY()-a.getY();
		double ACx = c.getX()-a.getX();
		double ACy = c.getY()-a.getY();
		
		double cp1 = (ABx)*(ACy) - (ABy)*(ACx);
		
		return (cp1>0);
	}

	public double distance(Point2D a, Point2D b, Point2D c) {
		
		double ABx = b.getX()-a.getX();
		double ABy = b.getY()-a.getY();
		
		double num = ABx*(a.getY()-c.getY())-ABy*(a.getX()-c.getX());
		
		if (num < 0) {
			num = -num;
		}
		
		return num;
	}

}
