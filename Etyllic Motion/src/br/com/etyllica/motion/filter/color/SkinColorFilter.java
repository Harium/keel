package br.com.etyllica.motion.filter.color;



public class SkinColorFilter extends ColorFilter{
	
	public SkinColorFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public boolean validateColor(int rgb) {
		return isSkin(rgb, tolerance);
	}
	
}
