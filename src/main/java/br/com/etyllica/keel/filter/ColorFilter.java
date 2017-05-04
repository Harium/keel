package br.com.etyllica.keel.filter;

import java.awt.Color;
import java.util.List;

import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.search.flood.SoftFloodFillSearch;

public class ColorFilter extends ColorPointFilter {
	
	protected int tolerance = 0x40; 
	
	public ColorFilter(int w, int h) {
		this(w, h, Color.BLACK);
	}
	
	public ColorFilter(int w, int h, Color color) {
		super(w, h);

		this.searchStrategy = new SoftFloodFillSearch(w, h);
		colorStrategy = new ColorStrategy(color, tolerance);
		searchStrategy.setPixelStrategy(colorStrategy);
	}
	
	public ColorFilter(int w, int h, Color color, int tolerance) {
		this(w, h, color);
		this.tolerance = tolerance;
		colorStrategy.setTolerance(tolerance);
	}
	
	@Override
	public List<Component> filter(ImageSource bimg, Component component) {
		//Setup happens on filter
		return searchStrategy.filter(bimg, component);
	}

	public ColorStrategy getColorStrategy() {
		return colorStrategy;
	}

	@Override
	public void setTolerance(int tolerance) {
		super.setTolerance(tolerance);
		this.tolerance = tolerance;
	}
	
	public int getTolerance() {
		return tolerance;
	}
				
}
