package br.com.etyllica.motion.custom.wand;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.ElasticFilter;

public class MagicWandFilter extends ElasticFilter {

	public MagicWandFilter(int w, int h) {
		super(w, h);
	}

	public List<Component> filter(BufferedImage bimg, Component component){
		
		List<Component> result = new ArrayList<Component>();
		
		return result;
	}

	@Override
	public boolean validateColor(int rgb) {
		return isBlack(rgb);
	}

	@Override
	public boolean validateComponent(Component component) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
