package br.com.etyllica.motion.filter.color;

import br.com.etyllica.motion.filter.SoftPixelStrategy;
import br.com.etyllica.util.EtyllicaMath;

public class SkinColorPeerStrategy extends SimpleToleranceStrategy implements SoftPixelStrategy {
		
	public SkinColorPeerStrategy() {
		super();
	}
	
	public SkinColorPeerStrategy(int tolerance) {
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
		
		int r = ColorStrategy.getRed(rgb);
		int g = ColorStrategy.getGreen(rgb);
		int b = ColorStrategy.getBlue(rgb);

		boolean individual = (r > 95 && g > 40 && b > 20);
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
	public boolean strongValidateColor(int baseColor, int rgb) {
		return validateColor(rgb);
	}
	
}
