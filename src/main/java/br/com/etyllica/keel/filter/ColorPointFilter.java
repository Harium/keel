package br.com.etyllica.keel.filter;

import java.util.List;

import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.search.ColoredPointSearch;
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
