package br.com.etyllica.motion.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.flood.ExpandableFloodFillSearch;

public class ExpandableColorFilter extends ColorPointFilter {
	
	protected int tolerance = 0x40; 
	
	public ExpandableColorFilter(int w, int h) {
		this(w, h, Color.BLACK);
	}
	
	public ExpandableColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new ExpandableFloodFillSearch(w, h);
		colorStrategy = new ColorStrategy(color, tolerance);
		searchStrategy.setPixelStrategy(colorStrategy);
	}
	
	public ExpandableColorFilter(int w, int h, Color color, int tolerance) {
		this(w, h, color);
		colorStrategy.setTolerance(tolerance);
	}
	
	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		searchStrategy.setup();
		return searchStrategy.filter(bimg, component);
	}

	public ColorStrategy getColorStrategy() {
		return colorStrategy;
	}
			
}
