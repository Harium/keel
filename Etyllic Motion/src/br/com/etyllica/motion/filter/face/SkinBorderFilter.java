package br.com.etyllica.motion.filter.face;

import br.com.etyllica.motion.filter.wand.BorderFilter;

public class SkinBorderFilter extends BorderFilter {
	
	public SkinBorderFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public boolean validateColor(int rgb) {
		return isSkin(rgb);
	}

}
