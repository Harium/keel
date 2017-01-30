package br.com.etyllica.motion.filter.dumb;

import br.com.etyllica.motion.filter.SoftPixelStrategy;

public class DumbColorFilter implements SoftPixelStrategy {

	@Override
	public boolean validateColor(int rgb, int j, int i) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean strongValidateColor(int baseColor, int j, int i, int rgb) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
