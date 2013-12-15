package br.com.etyllica.motion.filter.color;

import java.awt.Color;

public class BlackFilter extends BorderFilter {

	protected int tolerance = 0x40;
	
	public BlackFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public boolean validateColor(int rgb) {
		return isColor(rgb, Color.BLACK.getRGB(), this.tolerance);		
	}
	
}
