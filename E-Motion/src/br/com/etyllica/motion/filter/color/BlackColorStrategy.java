package br.com.etyllica.motion.filter.color;

import java.awt.Color;

public class BlackColorStrategy extends SimpleToleranceStrategy {

	@Override
	public boolean validateColor(int rgb) {
		return ColorStrategy.isColor(rgb, Color.BLACK.getRGB(), this.tolerance);
	}
	
}
