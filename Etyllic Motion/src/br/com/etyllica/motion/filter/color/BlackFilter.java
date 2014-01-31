package br.com.etyllica.motion.filter.color;

import java.awt.Color;

public class BlackFilter extends ColorStrategy {

	@Override
	public boolean validateColor(int rgb) {
		return isColor(rgb, Color.BLACK.getRGB(), this.tolerance);		
	}
	
}
