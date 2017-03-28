package br.com.etyllica.keel.filter.color;

import java.awt.Color;

public class BlackColorStrategy extends SimpleToleranceStrategy {

	@Override
	public boolean validateColor(int rgb, int j, int i) {
		return ColorStrategy.isColor(rgb, Color.BLACK.getRGB(), this.tolerance);
	}
	
}
