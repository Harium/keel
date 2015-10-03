package br.com.etyllica.motion.filter.color.skin;

import br.com.etyllica.motion.filter.SoftPixelStrategy;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.SimpleToleranceStrategy;
import br.com.etyllica.util.EtyllicaMath;

/**
 * Based on: Jure Kovač, Peter Peer, and Franc Solina - Human Skin Colour Clustering for Face Detection
 *
 */
public class SkinColorKovacNewStrategy extends SimpleToleranceStrategy implements SoftPixelStrategy {
		
	public SkinColorKovacNewStrategy() {
		super();
	}
	
	public SkinColorKovacNewStrategy(int tolerance) {
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
		
		final int R_MIN = 95-tolerance/2;//Default is 95
		final int G_MIN = 40-tolerance/3;//Default is 40
		final int B_MIN = 20-tolerance/4;//Default is 20
		
		final double RG_MOD = EtyllicaMath.diffMod(R, G);
		final double RB_MOD = EtyllicaMath.diffMod(R, B);
		
		//Standard illumination
		boolean firstRule = (R > R_MIN && G > G_MIN && B > B_MIN) &&
				             R - Math.min(G,B) > 15 &&
				             RG_MOD >= 10;
		//Strong illumination
		boolean secondRule = R > 220 && G > 210 && B > 170 &&
							 RG_MOD <= 15 && R > B && G > B;
		
		//Darker skin color
		boolean thirdRule = R <= 125 && G > G_MIN && B > B_MIN &&
							R >= G && R >= B &&
							RG_MOD >= 5 && RB_MOD >= 20;
							
				             //105 92 73 RG_MOD=13
				             //99 86 67 RG_MOD=13 R-B = 32 
				             //105 93 71 RG_MOD=12 R-B = 34
				             //103 96 78 RG_MOD=7 
				             //96 89 73 RG_MOD=7
							//122 123 107
							//125 126 110
							//105 104 99
							//101 91 89
		
		return firstRule || secondRule || thirdRule;
	}

	@Override
	public boolean strongValidateColor(int baseColor, int rgb) {
		return validateColor(rgb);
	}
	
}
