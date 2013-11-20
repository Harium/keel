package br.com.etyllica.motion.core;


public class SkinColorFilter extends ColorFilter{
	
	@Override
	public boolean validateColor(int rgb) {
		return isSkin(rgb, tolerance);
	}
	
}
