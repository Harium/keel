package br.com.etyllica.keel.filter.color;

import br.com.etyllica.keel.core.helper.ColorHelper;

import java.awt.Color;


public class DarkerColorStrategy extends ColorStrategy {

	public DarkerColorStrategy(Color color, int tolerance) {
		super(color, tolerance);		
	}
	
	@Override
	public boolean validateColor(int rgb, int j, int i) {
		return ColorHelper.isDarkerColor(rgb, this.color, this.minToleranceRed, this.minToleranceGreen, this.minToleranceBlue);
	}
		
}
