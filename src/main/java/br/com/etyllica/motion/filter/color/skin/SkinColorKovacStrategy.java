package br.com.etyllica.motion.filter.color.skin;

import br.com.etyllica.motion.filter.SoftPixelStrategy;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.SimpleToleranceStrategy;
import br.com.etyllica.util.EtyllicaMath;

/**
 * Based on: Jure Kovač, Peter Peer, and Franc Solina - Human Skin Colour Clustering for Face Detection
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
		
		final int R_MAX = 95-tolerance/2;//Default is 95
		final int G_MAX = 40-tolerance/3;//Default is 40
		final int B_MAX = 20-tolerance/4;//Default is 20
		
		final double RG_MOD = EtyllicaMath.diffMod(R, G);
		
		boolean firstRule = (R > R_MAX && G > G_MAX && B > B_MAX) &&
				             R - Math.min(G,B) > 15 &&
				             RG_MOD > 15;
		
		boolean secondRule = R > 220 && G > 210 && B > 170 &&
							 RG_MOD <= 15 && R > B && G > B;
		
		return firstRule || secondRule;
	}

	private static int max(int r, int g, int b) {
		return Math.max(Math.max(r, g),b);
	}
	
	private static int min(int r, int g, int b) {
		return Math.min(Math.min(r, g),b);
	}

	@Override
	public boolean strongValidateColor(int baseColor, int rgb) {
		return validateColor(rgb);
	}
	
}
