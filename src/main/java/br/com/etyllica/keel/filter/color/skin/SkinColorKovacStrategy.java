package br.com.etyllica.keel.filter.color.skin;

import br.com.etyllica.keel.filter.SoftPixelStrategy;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.color.SimpleToleranceStrategy;
import br.com.etyllica.util.math.EtyllicaMath;

/**
 * Based on: http://academic.aua.am/Skhachat/Public/Papers%20on%20Face%20Detection/RGB-H-CbCr%20Skin%20Colour%20Model%20for%20Human%20Face%20Detection.pdf
 *
 */
public class SkinColorKovacStrategy extends SimpleToleranceStrategy implements SoftPixelStrategy {
		
	public SkinColorKovacStrategy() {
		super();
	}
	
	public SkinColorKovacStrategy(int tolerance) {
		super(tolerance);
	}

	@Override
	public boolean validateColor(int rgb, int j, int i) {
		return isSkin(rgb, tolerance);
	}
	
	public static boolean isSkin(int rgb) {
		return isSkin(rgb, 0);
	}
	
	public static boolean isSkin(int rgb, int tolerance) {
		
		int r = ColorStrategy.getRed(rgb);
		int g = ColorStrategy.getGreen(rgb);
		int b = ColorStrategy.getBlue(rgb);
		
		final int R_MAX = 70;//Default is 95
		final int G_MAX = 30;//Default is 40
		final int B_MAX = 20;//Default is 20
		
		boolean individual = (r > R_MAX && g > G_MAX && b > B_MAX);
		boolean interval = max(r,g,b) - min(r,g,b) > 15;
		boolean dif = EtyllicaMath.diffMod(r, g) > 15 && r > g && r > b;			
		
		return individual&&interval&&dif;
	}

	private static int max(int r, int g, int b) {
		return Math.max(Math.max(r, g),b);
	}
	
	private static int min(int r, int g, int b) {
		return Math.min(Math.min(r, g),b);
	}

	@Override
	public boolean strongValidateColor(int baseColor, int rgb, int j, int i) {
		return validateColor(rgb, j, i);
	}
	
}
