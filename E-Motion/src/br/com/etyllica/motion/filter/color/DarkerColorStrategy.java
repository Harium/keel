package br.com.etyllica.motion.filter.color;

import java.awt.Color;

import br.com.etyllica.motion.filter.ColorPointFilter;


public class DarkerColorStrategy extends ColorStrategy {

	public DarkerColorStrategy(Color color, int tolerance) {
		super(color, tolerance);		
	}
	
	@Override
	public boolean validateColor(int rgb) {
		return ColorStrategy.isDarkerColor(rgb, this.color, this.minToleranceRed, this.minToleranceGreen, this.minToleranceBlue);
	}
		
}
