package br.com.etyllica.keel.filter.dummy;

import br.com.etyllica.keel.filter.SoftPixelStrategy;

public class DummyPixelStrategy implements SoftPixelStrategy {

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
