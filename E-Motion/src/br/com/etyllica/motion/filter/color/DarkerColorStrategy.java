package br.com.etyllica.motion.filter.color;

import java.awt.Color;


public class DarkerColorStrategy extends ColorStrategy {

	public DarkerColorStrategy(Color color, int tolerance) {
		super(color, tolerance);
	}
	
	@Override
	public boolean validateColor(int rgb) {
		return isDarkerColor(rgb, this.color, this.tolerance);
	}
		
}
