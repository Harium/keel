package br.com.etyllica.motion.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.FloodFillSearch;

public class TrackingByColorFilter extends ColorFilter {
	
	public TrackingByColorFilter(int w, int h) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
	}
	
	public TrackingByColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new FloodFillSearch(w, h);
		
		colorStrategy = new ColorStrategy(color, 0x40);
		
		searchStrategy.setPixelStrategy(colorStrategy);
				
	}
	
	public TrackingByColorFilter(int w, int h, Color color, int tolerance) {
		this(w, h, color);

		colorStrategy.setTolerance(tolerance);
		
	}
	
	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		
		((FloodFillSearch)searchStrategy).resetMask(bimg.getWidth(), bimg.getHeight());
		
		return searchStrategy.filter(bimg, component);
	}
			
}
