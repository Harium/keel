package com.harium.keel.custom;

import java.util.ArrayList;
import java.util.List;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;
import com.harium.keel.feature.hull.HullComponent;
import com.harium.keel.filter.ColorFilter;
import com.harium.keel.filter.search.flood.SoftFloodFillSearch;
import com.harium.keel.modifier.hull.FastConvexHullModifier;
import com.harium.keel.modifier.hull.HullModifier;
import com.harium.keel.modifier.hull.PathCompressionModifier;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.linear.Point2D;

public class PolygonFilter {

	private ColorFilter colorFilter;
	
	private HullModifier<HullComponent> quickHull;
	private PathCompressionModifier pathCompressionModifier;
	
	public PolygonFilter(int w, int h, Color color) {
		colorFilter = new ColorFilter(w, h, color);
		
		SoftFloodFillSearch floodFill = (SoftFloodFillSearch)colorFilter.getSearchStrategy();
		floodFill.setStep(1);
		
		quickHull = new FastConvexHullModifier();
		
		pathCompressionModifier = new PathCompressionModifier(5);
	}
	
	public List<Component> filter(ImageSource image, Component screen) {
		List<Component> components = colorFilter.filter(image, screen);
		
		System.out.println("First pass: "+components.size());
		
		List<Component> filtered = new ArrayList<Component>();
		
		for(Component component:components) {
			HullComponent hull = quickHull.modify(component);
			System.out.println("Second pass: "+hull.getPointCount());
			
			//filtered.add(hull);
			
			List<Point2D> points = pathCompressionModifier.modify(hull);
			filtered.add(new Component(points));
			//filtered.add(hull);
		}
		
		return filtered;
	}
	
}
