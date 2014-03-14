package br.com.etyllica.motion.filter.modifier;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.helper.PointListHelper;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;

public class QuickHullModifier implements ComponentModifierStrategy {

	public QuickHullModifier() {
		super();
	}

	@Override
	public Component modifyComponent(Component component) {
				
		List<Point2D> convexPolygon = quickHull(component.getPoints());
		
		Component polygon = new Component(0, 0);
		
		for(Point2D ponto: convexPolygon) {
			polygon.add(ponto);
		}
		
		return polygon;
		
	}
		
	//Based on www.ahristov.com/tutorial/geometry-games/convex-hull.html
	public List<Point2D> quickHull(List<Point2D> points) {

		List<Point2D> clone = PointListHelper.cloneList(points);
		
		if (points.size() < 3) return clone;

		List<Point2D> convexHull = new ArrayList<Point2D>();

		// find extremals
		int minPoint = -1, maxPoint = -1;
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		
		for (int i = 0; i < clone.size(); i++) {
			if (clone.get(i).getX() < minX) {
				minX = clone.get(i).getX();
				minPoint = i;
			}
			
			if (clone.get(i).getX() > maxX) {
				maxX = clone.get(i).getX();
				maxPoint = i;       
			}
		}
		
		Point2D A = clone.get(minPoint);
		Point2D B = clone.get(maxPoint);
		
		convexHull.add(A);
		convexHull.add(B);
		
		clone.remove(A);
		clone.remove(B);

		ArrayList<Point2D> leftSet = new ArrayList<Point2D>();
		ArrayList<Point2D> rightSet = new ArrayList<Point2D>();

		for (int i = 0; i < clone.size(); i++) {
			Point2D p = clone.get(i);
			if (pointLocation(A,B,p) == -1)
				leftSet.add(p);
			else
				rightSet.add(p);
		}
		
		hullSet(A,B,rightSet,convexHull);
		
		hullSet(B,A,leftSet,convexHull);

		return convexHull;
	}

	public void hullSet(Point2D a, Point2D b, ArrayList<Point2D> list, List<Point2D> hull) {
		
		if (list.size() == 0) return;
		
		int insertPosition = hull.indexOf(b);
		
		if (list.size() == 1) {
			Point2D p = list.get(0);
			list.remove(p);
			hull.add(insertPosition,p);
			return;
		}
				
		Point2D point = null;
		
		double dist = Double.MIN_VALUE;
		
		for (Point2D p : list) {
			
			double distance  = distance(a,b,p);
			
			if (distance > dist) {
				dist = distance;
				point = p;
			}
		}
				
		list.remove(point);
		hull.add(insertPosition,point);

		// Determine who's to the left of AP
		ArrayList<Point2D> leftSetAP = new ArrayList<Point2D>();
		for (Point2D M: list) {
			
			if (pointLocation(a,point,M)==1) {

				leftSetAP.add(M);
				
			}
			
		}

		// Determine who's to the left of PB
		ArrayList<Point2D> leftSetPB = new ArrayList<Point2D>();
		for (Point2D M: list) {
			
			if (pointLocation(point,b,M)==1) {
			
				leftSetPB.add(M);
				
			}
			
		}
		
		hullSet(a,point,leftSetAP,hull);
		hullSet(point,b,leftSetPB,hull);
	}

	public double pointLocation(Point2D a, Point2D b, Point2D c) {
		double ABx = b.getX()-a.getX();
		double ABy = b.getY()-a.getY();
		double ACx = c.getX()-a.getX();
		double ACy = c.getY()-a.getY();
		
		double cp1 = (ABx)*(ACy) - (ABy)*(ACx);
		
		return (cp1>0)?1:-1;		
	}

	public double distance(Point2D a, Point2D b, Point2D c) {
		double ABx = b.getX()-a.getX();
		double ABy = b.getY()-a.getY();
		double num = ABx*(a.getY()-c.getY())-ABy*(a.getX()-c.getX());
		
		if (num < 0) num = -num;
		return num;
	}

}
