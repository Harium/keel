package br.com.etyllica.motion.custom;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.dumb.DumbComponentFilter;
import br.com.etyllica.motion.filter.modifier.EnvelopeModifier;
import br.com.etyllica.motion.filter.search.FloodFillSearch;

public class BarCodeFilter extends CustomFilter {
	
	private FloodFillSearch searchStrategy;
	
	public BarCodeFilter(int w, int h){
		super();
		
		colorStrategy = new ColorStrategy(Color.BLACK);
		
		componentStrategy = new DumbComponentFilter();
		
		modifierStrategy = new EnvelopeModifier();
		

		searchStrategy = new FloodFillSearch(w, h);
		
		searchStrategy.setPixelStrategy(colorStrategy);
		
		searchStrategy.setComponentStrategy(componentStrategy);
		
		searchStrategy.setComponentModifierStrategy(modifierStrategy);
		
	}

	@Override
	public Component filterFirst(BufferedImage bimg, Component component) {
		
		return searchStrategy.filterFirst(bimg, component);
		
	}

	@Override
	public List<Component> filter(BufferedImage bimg, Component component) {
		return searchStrategy.filter(bimg, component);
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub	
	}
	
}
