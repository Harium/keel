package br.com.etyllica.motion.filter;

import br.com.etyllica.motion.core.strategy.PixelStrategy;

public interface SoftPixelStrategy extends PixelStrategy {
	public boolean weakValidateColor(int baseColor, int rgb);
}
