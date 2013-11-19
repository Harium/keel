package br.com.etyllica.motion.filter.wand;

import br.com.etyllica.motion.features.Component;


public class BlackWandFilter extends MagicWandBoxFilter {

	public BlackWandFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public boolean validateColor(int rgb) {
		return isGrey(rgb, 150);
	}
	
	@Override
	public boolean validateComponent(Component component) {
		
		return component.getPoints().size()>=4;
	}
	
	private boolean isGrey(int rgb, int tolerance){
		
		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);
		
		if(red>tolerance&&green>tolerance&&blue>tolerance){
			return false;
		}
		
		return difMod(red, green)<30&&difMod(red, blue)<30;
		
	}
	
}
