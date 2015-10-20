package br.com.etyllica.motion.custom;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.feature.hull.HullComponent;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.search.flood.SoftFloodFillSearch;
import br.com.etyllica.motion.modifier.hull.FastConvexHullModifier;
import br.com.etyllica.motion.modifier.hull.HullModifier;
import br.com.etyllica.motion.modifier.hull.PathCompressionModifier;

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
	
	public List<Component> filter(BufferedImage image, Component screen) {
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
