package br.com.etyllica.motion.filter.dumb;

import br.com.etyllica.motion.filter.SoftPixelStrategy;

public class DumbColorFilter implements SoftPixelStrategy {

	@Override
	public boolean validateColor(int rgb) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean weakValidateColor(int baseColor, int rgb) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
