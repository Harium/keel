package br.com.etyllica.motion.filter.modifier;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.strategy.ComponentModifierStrategy;
import br.com.etyllica.motion.features.Component;

public class QuickHullModifier implements ComponentModifierStrategy {

	public QuickHullModifier() {
		super();
	}

	@Override
	public Component modifyComponent(Component component) {
				
		List<Point2D> convexPolygon = quickHull(component.getPoints());
		
		Component poly = new Component(0, 0);
		
		for(Point2D ponto: convexPolygon){
			poly.add(ponto);
		}
		
		return poly;
		
	}
		
	//From www.ahristov.com/tutorial/geometry-games/convex-hull.html
	public List<Point2D> quickHull(List<Point2D> points){

		//if (points.size() < 3) return (ArrayList)points.clone();
		if (points.size() < 3) return points;

		List<Point2D> convexHull = new ArrayList<Point2D>();

		// find extremals
		int minPoint = -1, maxPoint = -1;
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		
		for (int i = 0; i < points.size(); i++){
			if (points.get(i).getX() < minX){
				minX = points.get(i).getX();
				minPoint = i;
			}
			
			if (points.get(i).getX() > maxX){
				maxX = points.get(i).getX();
				maxPoint = i;       
			}
		}
		
		Point2D A = points.get(minPoint);
		Point2D B = points.get(maxPoint);
		
		convexHull.add(A);
		convexHull.add(B);
		
		points.remove(A);
		points.remove(B);

		ArrayList<Point2D> leftSet = new ArrayList<Point2D>();
		ArrayList<Point2D> rightSet = new ArrayList<Point2D>();

		for (int i = 0; i < points.size(); i++){
			Point2D p = points.get(i);
			if (pointLocation(A,B,p) == -1)
				leftSet.add(p);
			else
				rightSet.add(p);
		}
		
		hullSet(A,B,rightSet,convexHull);
		
		hullSet(B,A,leftSet,convexHull);

		return convexHull;
	}

	public void hullSet(Point2D A, Point2D B, ArrayList<Point2D> set, List<Point2D> hull){
		
		int insertPosition = hull.indexOf(B);
		if (set.size() == 0) return;
		if (set.size() == 1){
			Point2D p = set.get(0);
			set.remove(p);
			hull.add(insertPosition,p);
			return;
		}
		double dist = Double.MIN_VALUE;
		int furthestPoint = -1;
		for (int i = 0; i < set.size(); i++){
			Point2D p = set.get(i);
			double distance  = distance(A,B,p);
			if (distance > dist){
				dist = distance;
				furthestPoint = i;
			}
		}
		Point2D P = set.get(furthestPoint);
		set.remove(furthestPoint);
		hull.add(insertPosition,P);

		// Determine who's to the left of AP
		ArrayList<Point2D> leftSetAP = new ArrayList<Point2D>();
		for (int i = 0; i < set.size(); i++){
			Point2D M = set.get(i);
			if (pointLocation(A,P,M)==1){
				//set.remove(M);
				leftSetAP.add(M);
			}
		}

		// Determine who's to the left of PB
		ArrayList<Point2D> leftSetPB = new ArrayList<Point2D>();
		for (int i = 0; i < set.size(); i++){
			Point2D M = set.get(i);
			if (pointLocation(P,B,M)==1){
				//set.remove(M);
				leftSetPB.add(M);
			}
		}
		hullSet(A,P,leftSetAP,hull);
		hullSet(P,B,leftSetPB,hull);
	}

	public double pointLocation(Point2D A, Point2D B, Point2D P){
		double cp1 = (B.getX()-A.getX())*(P.getY()-A.getY()) - (B.getY()-A.getY())*(P.getX()-A.getX());
		return (cp1>0)?1:-1;
	}

	public double distance(Point2D a, Point2D b, Point2D c){
		double ABx = b.getX()-a.getX();
		double ABy = b.getY()-a.getY();
		double num = ABx*(a.getY()-c.getY())-ABy*(a.getX()-c.getX());
		if (num < 0) num = -num;
		return num;
	}

}
