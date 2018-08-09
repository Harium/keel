package com.harium.keel.custom;

import java.util.ArrayList;
import java.util.List;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.feature.hull.HullFeature;
import com.harium.keel.filter.ColorFilter;
import com.harium.keel.filter.search.flood.SoftFloodFillSearch;
import com.harium.keel.modifier.hull.FastConvexHullModifier;
import com.harium.keel.modifier.hull.HullModifier;
import com.harium.keel.modifier.hull.PathCompressionModifier;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.geometry.Point2D;

public class PolygonFilter {

	private ColorFilter colorFilter;
	
	private HullModifier<HullFeature> quickHull;
	private PathCompressionModifier pathCompressionModifier;
	
	public PolygonFilter(int w, int h, Color color) {
		colorFilter = new ColorFilter(w, h, color);
		
		SoftFloodFillSearch floodFill = (SoftFloodFillSearch)colorFilter.getSearchStrategy();
		floodFill.setStep(1);
		
		quickHull = new FastConvexHullModifier();
		
		pathCompressionModifier = new PathCompressionModifier(5);
	}
	
	public List<PointFeature> filter(ImageSource image, Feature screen) {
		List<PointFeature> components = colorFilter.filter(image, screen);
		
		System.out.println("First pass: "+components.size());
		
		List<PointFeature> filtered = new ArrayList<PointFeature>();
		
		for(PointFeature component:components) {
			HullFeature hull = quickHull.modify(component);
			System.out.println("Second pass: "+hull.getPointCount());
			
			//filtered.add(hull);
			
			List<Point2D> points = pathCompressionModifier.modify(hull);
			filtered.add(new PointFeature(points));
			//filtered.add(hull);
		}
		
		return filtered;
	}
	
}
