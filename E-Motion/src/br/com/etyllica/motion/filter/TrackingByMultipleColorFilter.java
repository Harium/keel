package br.com.etyllica.motion.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.MultipleColorStrategy;
import br.com.etyllica.motion.filter.search.FloodFillSearch;

public class TrackingByMultipleColorFilter extends TrackingFilter {
	
	private MultipleColorStrategy colorStrategy;
	
	public TrackingByMultipleColorFilter(int w, int h) {
		super(new FloodFillSearch(w, h));
		
		colorStrategy = new MultipleColorStrategy();
		
		this.searchStrategy.setPixelStrategy(colorStrategy);
	}
	
	public TrackingByMultipleColorFilter(int w, int h, Color color, int tolerance) {
		this(w, h);
		
		colorStrategy.addColor(color, tolerance);
		
	}
	
	public TrackingByMultipleColorFilter(int w, int h, Color color) {
		this(w, h, color, 0x40);
	}
	
	public List<Component> filter(BufferedImage bimg, Component component) {
		
		((FloodFillSearch)searchStrategy).resetMask(bimg.getWidth(), bimg.getHeight());
		
		return searchStrategy.filter(bimg, component);
	}

	public void addColor(Color color, int tolerance) {
		colorStrategy.addColor(color, tolerance);
	}
	
	public void addColor(Color color, int redTolerance, int greenTolerance, int blueTolerance) {
		colorStrategy.addColor(color, redTolerance, greenTolerance, blueTolerance);
	}
		
	public void addColor(Color color, int maxTolerance, int minTolerance) {
		colorStrategy.addColor(color, maxTolerance, minTolerance);
	}
			
}
