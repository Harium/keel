package br.com.etyllica.motion.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.ColoredPointSearch;

public class ColorPointFilter extends TrackingFilter {

	protected ColorStrategy colorStrategy;
		
	public ColorPointFilter(int w, int h) {
		super(new ColoredPointSearch(w, h));
		
		colorStrategy = new ColorStrategy(Color.BLACK);
		searchStrategy.setPixelStrategy(colorStrategy);
	}
		
	public ColorPointFilter(int w, int h, Color color) {
		this(w, h);
		
		colorStrategy.setColor(color);
	}

	public Component filterFirst(BufferedImage bimg, Component component) {
		
		return searchStrategy.filterFirst(bimg, component);
		
	}
	
	public List<Component> filter(BufferedImage bimg, Component component) {
		
		return searchStrategy.filter(bimg, component);
	}

	public void setTolerance(int tolerance) {
		colorStrategy.setTolerance(tolerance);
	}
	
	public int getColor() {
		return colorStrategy.getColor();
	}

	public void setColor(Color color) {
		colorStrategy.setColor(color);
	}

}
