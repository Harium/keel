package br.com.etyllica.motion.filter.color.skin;

import br.com.etyllica.motion.filter.SoftPixelStrategy;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.SimpleToleranceStrategy;

/**
 * Based on: Nusirwan Anwar bin Abdul Rahman, Kit Chong Wei and John See
 * RGB-H-CbCr Skin Colour Model for Human Face Detection  
 */
public class SkinColorRGBHCbCrStrategy extends SimpleToleranceStrategy implements SoftPixelStrategy {
		
	public SkinColorRGBHCbCrStrategy() {
		super();
	}
	
	public SkinColorRGBHCbCrStrategy(int tolerance) {
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
		boolean ruleA = SkinColorKovacStrategy.isSkin(rgb);
		
		final int R = ColorStrategy.getRed(rgb);
		final int G = ColorStrategy.getGreen(rgb);
		final int B = ColorStrategy.getBlue(rgb);
		
		//final int Y  = ColorStrategy.getY(R,G,B);
		final int CB = ColorStrategy.getCB(R,G,B);
		final int CR = ColorStrategy.getCR(R,G,B);
		
		final float H  = ColorStrategy.getH(R,G,B);
		
		boolean rule3 = CR <= 1.5862 * CB + 20;
		boolean rule4 = CR >= 0.3448 * CB + 76.2069;
		boolean rule5 = CR >= -4.5652 * CB + 234.5652;
		boolean rule6 = CR <= -1.15 * CB + 301.75;
		boolean rule7 = CR <= -2.2857 * CB + 432.8;
		
		boolean ruleB = rule3&&rule4&&rule5&&rule6&&rule7;
		boolean ruleC = H < 25 && H > 230;
		
		return ruleA && ruleB && ruleC;
	}

	@Override
	public boolean strongValidateColor(int baseColor, int rgb) {
		return validateColor(rgb);
	}
	
}
