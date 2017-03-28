package br.com.etyllica.keel.filter;

import br.com.etyllica.keel.core.strategy.PixelStrategy;

public interface SoftPixelStrategy extends PixelStrategy {
	public boolean strongValidateColor(int baseColor, int rgb, int j, int i);
}
