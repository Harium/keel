package br.com.etyllica.motion.filter.color.skin;

import br.com.etyllica.motion.filter.SoftPixelStrategy;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.SimpleToleranceStrategy;

public class SkinColorStrategy extends SimpleToleranceStrategy implements SoftPixelStrategy {
		
	public SkinColorStrategy() {
		super();
	}
	
	public SkinColorStrategy(int tolerance) {
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

		float x = r;
		float y = b+(g-b);

		float maxTolerance = tolerance;
		float minTolerance = tolerance;
		
		float my=(8*x)/9-40/9;

		if(x>105&&x<175){
			minTolerance = tolerance*1.3f;
		}

		return x>40&&x<230&&(y>my-minTolerance&&y<my+maxTolerance);
	}

	@Override
	public boolean strongValidateColor(int baseColor, int rgb) {
		return validateColor(rgb);
	}
	
}
