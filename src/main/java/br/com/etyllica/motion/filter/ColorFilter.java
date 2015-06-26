package br.com.etyllica.motion.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.SoftFloodFillSearch;

public class ColorFilter extends ColorPointFilter {
		
	public ColorFilter(int w, int h) {
		this(w, h, Color.BLACK);
	}
	
	public ColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new SoftFloodFillSearch(w, h);
		
		colorStrategy = new ColorStrategy(color, 0x40);
		
		searchStrategy.setPixelStrategy(colorStrategy);
	}
	
	public ColorFilter(int w, int h, Color color, int tolerance) {
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
