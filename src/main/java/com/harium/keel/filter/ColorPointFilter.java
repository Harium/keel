package com.harium.keel.filter;

import java.util.List;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;
import com.harium.keel.filter.color.ColorStrategy;
import com.harium.keel.filter.search.ColoredPointSearch;
import com.harium.etyl.commons.graphics.Color;

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

	public Component filterFirst(ImageSource bimg, Component component) {
		return searchStrategy.filterFirst(bimg, component);
	}
	
	public List<Component> filter(ImageSource bimg, Component component) {
		return searchStrategy.filter(bimg, component);
	}

	public void setTolerance(int tolerance) {
		colorStrategy.setTolerance(tolerance);
	}
	
	public int getColor() {
		return colorStrategy.getColor();
	}

	public void setColor(int color) {
		colorStrategy.setColor(color);
	}

	public void setColor(Color color) {
		colorStrategy.setColor(color);
	}

}
