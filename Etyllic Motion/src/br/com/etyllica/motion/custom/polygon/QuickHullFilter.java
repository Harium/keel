package br.com.etyllica.motion.custom.polygon;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.ElasticFilter;

public class QuickHullFilter extends ElasticFilter{

	private Polygon polygon = new Polygon();
	
	public QuickHullFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public boolean validateColor(int rgb) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean validateComponent(Component component) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {

		polygon.reset();
		
		List<Component> result = new ArrayList<Component>();
		
		Component poly = new Component(w, h);
		
		for(Ponto2D ponto: quickHull(component.getPoints())){
			polygon.addPoint((int)ponto.getX(), (int)ponto.getY());
			poly.add(ponto);
		}
		
		result.add(poly);

		return result;
	}

	//From www.ahristov.com/tutorial/geometry-games/convex-hull.html
	public List<Ponto2D> quickHull(List<Ponto2D> points) {

		//if (points.size() < 3) return (ArrayList)points.clone();
		if (points.size() < 3) return points;

		List<Ponto2D> convexHull = new ArrayList<Ponto2D>();

		// find extremals
		int minPoint = -1, maxPoint = -1;
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getX() < minX) {
				minX = points.get(i).getX();
				minPoint = i;
			} 
			if (points.get(i).getX() > maxX) {
				maxX = points.get(i).getX();
				maxPoint = i;       
			}
		}
		Ponto2D A = points.get(minPoint);
		Ponto2D B = points.get(maxPoint);
		convexHull.add(A);
		convexHull.add(B);
		points.remove(A);
		points.remove(B);

		ArrayList<Ponto2D> leftSet = new ArrayList<Ponto2D>();
		ArrayList<Ponto2D> rightSet = new ArrayList<Ponto2D>();

		for (int i = 0; i < points.size(); i++) {
			Ponto2D p = points.get(i);
			if (pointLocation(A,B,p) == -1)
				leftSet.add(p);
			else
				rightSet.add(p);
		}
		hullSet(A,B,rightSet,convexHull);
		hullSet(B,A,leftSet,convexHull);

		return convexHull;
	}

	public void hullSet(Ponto2D A, Ponto2D B, ArrayList<Ponto2D> set, List<Ponto2D> hull) {
		int insertPosition = hull.indexOf(B);
		if (set.size() == 0) return;
		if (set.size() == 1) {
			Ponto2D p = set.get(0);
			set.remove(p);
			hull.add(insertPosition,p);
			return;
		}
		double dist = Double.MIN_VALUE;
		int furthestPoint = -1;
		for (int i = 0; i < set.size(); i++) {
			Ponto2D p = set.get(i);
			double distance  = distance(A,B,p);
			if (distance > dist) {
				dist = distance;
				furthestPoint = i;
			}
		}
		Ponto2D P = set.get(furthestPoint);
		set.remove(furthestPoint);
		hull.add(insertPosition,P);

		// Determine who's to the left of AP
		ArrayList<Ponto2D> leftSetAP = new ArrayList<Ponto2D>();
		for (int i = 0; i < set.size(); i++) {
			Ponto2D M = set.get(i);
			if (pointLocation(A,P,M)==1) {
				//set.remove(M);
				leftSetAP.add(M);
			}
		}

		// Determine who's to the left of PB
		ArrayList<Ponto2D> leftSetPB = new ArrayList<Ponto2D>();
		for (int i = 0; i < set.size(); i++) {
			Ponto2D M = set.get(i);
			if (pointLocation(P,B,M)==1) {
				//set.remove(M);
				leftSetPB.add(M);
			}
		}
		hullSet(A,P,leftSetAP,hull);
		hullSet(P,B,leftSetPB,hull);
	}

	public double pointLocation(Ponto2D A, Ponto2D B, Ponto2D P) {
		double cp1 = (B.getX()-A.getX())*(P.getY()-A.getY()) - (B.getY()-A.getY())*(P.getX()-A.getX());
		return (cp1>0)?1:-1;
	}

	public double distance(Ponto2D a, Ponto2D b, Ponto2D c) {
		double ABx = b.getX()-a.getX();
		double ABy = b.getY()-a.getY();
		double num = ABx*(a.getY()-c.getY())-ABy*(a.getX()-c.getX());
		if (num < 0) num = -num;
		return num;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

}
