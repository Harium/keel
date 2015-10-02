package br.com.etyllica.motion.filter.color.skin;

import br.com.etyllica.motion.filter.SoftPixelStrategy;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.SimpleToleranceStrategy;
import br.com.etyllica.util.EtyllicaMath;

/**
 * Based on:
 * Reference: http://www.iosrjournals.org/iosr-jce/papers/Vol11-issue6/E01163138.pdf
 */
public class SkinColorCRStrategy extends SimpleToleranceStrategy implements SoftPixelStrategy {
		
	public SkinColorCRStrategy() {
		super();
	}
	
	public SkinColorCRStrategy(int tolerance) {
		super(tolerance);
	}

	@Override
	public boolean validateColor(int rgb) {
		return isSkin(rgb, tolerance);
	}
	
	public static boolean isSkin(int rgb) {
		return isSkin(rgb, 0);
	}
	
	public static boolean isSkin(int rgb, int tolerance) {
		
		final int R = ColorStrategy.getRed(rgb);
		final int G = ColorStrategy.getGreen(rgb);
		final int B = ColorStrategy.getBlue(rgb);
		
		final double Y = 0.299*R + 0.587*G + 0.114*B;
		final double CR = 0.5*R + (-0.418*G) + (-0.081*B);			
		
		return CR>1;
	}

	@Override
	public boolean strongValidateColor(int baseColor, int rgb) {
		return validateColor(rgb);
	}
	
}
