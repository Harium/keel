package com.harium.keel.modifier.hull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.harium.keel.feature.PointFeature;
import com.harium.keel.feature.hull.HullFeature;
import com.harium.keel.helper.PointListHelper;
import com.harium.etyl.geometry.Point2D;

/**
 * Found at http://code.google.com/p/convex-hull/source/browse/Convex+Hull/src/algorithms/FastConvexHull.java?r=4
 *
 */
public class FastConvexHullModifier implements HullModifier<HullFeature> {

	public FastConvexHullModifier() {
		super();
	}

	@Override
	public PointFeature modifyComponent(PointFeature component) {
		
		if(component.getPointCount() < 3) {
			return component;
		}
		
		List<Point2D> convexPolygon = quickHullList(component.getPoints());
		
		PointFeature polygon = new PointFeature();
		
		for(Point2D point: convexPolygon) {
			polygon.add(point);
		}
		
		return polygon;
	}

	public HullFeature modify(PointFeature feature) {
		HullFeature result = new HullFeature();
		
		if(feature.getPointCount() < 3) {
			result.addAll(feature.getPoints());
			return result;
		}
		
		List<Point2D> list = PointListHelper.cloneList(feature.getPoints());
		
		ArrayList<Point2D> orderedList = quickHullList(list);
		
		result.addAll(orderedList);

		return result;
	}

	protected ArrayList<Point2D> quickHullList(List<Point2D> list) {
		Collections.sort(list, new XCompare());

		int n = list.size();

		Point2D[] lUpper = new Point2D[n];

		lUpper[0] = list.get(0);
		lUpper[1] = list.get(1);

		int lUpperSize = 2;

		for (int i = 2; i < n; i++) {
			lUpper[lUpperSize] = list.get(i);
			lUpperSize++;

			while (lUpperSize > 2 && !Point2D.isRightTurn(lUpper[lUpperSize - 3], lUpper[lUpperSize - 2], lUpper[lUpperSize - 1])) {
				// Remove the middle point of the three last
				lUpper[lUpperSize - 2] = lUpper[lUpperSize - 1];
				lUpperSize--;
			}
		}

		Point2D[] lLower = new Point2D[n];

		lLower[0] = list.get(n - 1);
		lLower[1] = list.get(n - 2);

		int lLowerSize = 2;

		for (int i = n - 3; i >= 0; i--) {
			
			lLower[lLowerSize] = list.get(i);
			lLowerSize++;

			while (lLowerSize > 2 && !Point2D.isRightTurn(lLower[lLowerSize - 3], lLower[lLowerSize - 2], lLower[lLowerSize - 1]))
			{
				// Remove the middle point of the three last
				lLower[lLowerSize - 2] = lLower[lLowerSize - 1];
				lLowerSize--;
			}
		}

		ArrayList<Point2D> orderedList = new ArrayList<Point2D>();

		for (int i = 0; i < lUpperSize; i++) {
			orderedList.add(lUpper[i]);
		}

		for (int i = 1; i < lLowerSize - 1; i++) {
			orderedList.add(lLower[i]);
		}
		return orderedList;
	}

	private class XCompare implements Comparator<Point2D> {
		
		@Override
		public int compare(Point2D p1, Point2D p2) {
			if (p1.getX() < p2.getX()) return -1;
	        if (p1.getX() > p2.getX()) return 1;
	        return 0;
		}
		
	}
}
