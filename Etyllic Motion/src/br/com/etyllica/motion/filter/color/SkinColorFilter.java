package br.com.etyllica.motion.filter.color;



public class SkinColorFilter extends ColorFilter{
	
	@Override
	public boolean validateColor(int rgb) {
		return isSkin(rgb, tolerance);
	}
	
}
