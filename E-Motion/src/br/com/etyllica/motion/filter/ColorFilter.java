package br.com.etyllica.motion.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.core.strategy.SearchFilter;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.dumb.DumbComponentFilter;
import br.com.etyllica.motion.filter.search.ColoredPointSearch;

public class ColorFilter {

	protected ColorStrategy colorStrategy;
	
	protected SearchFilter searchStrategy;
	
	public ColorFilter(int w, int h) {
		super();
		
		searchStrategy = new ColoredPointSearch(w, h);
	}
		
	public ColorFilter(int w, int h, Color color) {
		this(w, h);
		
		colorStrategy = new ColorStrategy(color);
				
		searchStrategy.setPixelStrategy(colorStrategy);
		
		searchStrategy.setComponentStrategy(new DumbComponentFilter());
		
	}

	public Component filterFirst(BufferedImage bimg, Component component) {
		
		return searchStrategy.filterFirst(bimg, component);
		
	}
	
	public List<Component> filter(BufferedImage bimg, Component component) {
		
		return searchStrategy.filter(bimg, component);
	}

	public int getTolerance() {
		return colorStrategy.getTolerance();
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
