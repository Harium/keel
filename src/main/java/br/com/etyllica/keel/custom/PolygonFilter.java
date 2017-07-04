package br.com.etyllica.keel.custom;

import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.commons.graphics.Color;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.feature.hull.HullComponent;
import br.com.etyllica.keel.filter.ColorFilter;
import br.com.etyllica.keel.filter.search.flood.SoftFloodFillSearch;
import br.com.etyllica.keel.modifier.hull.FastConvexHullModifier;
import br.com.etyllica.keel.modifier.hull.HullModifier;
import br.com.etyllica.keel.modifier.hull.PathCompressionModifier;

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
