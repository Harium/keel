package br.com.etyllica.motion.filter.color;

public class SkinColorStrategy extends ToleranceStrategy {
	
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

		int r = getRed(rgb);
		int g = getGreen(rgb);
		int b = getBlue(rgb);

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
	
}
