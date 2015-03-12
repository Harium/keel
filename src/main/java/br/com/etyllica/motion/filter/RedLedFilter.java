package br.com.etyllica.motion.filter;

import java.awt.Color;

public class RedLedFilter extends ColorPointFilter {

	public RedLedFilter(int w, int h) {
		super(w, h, Color.RED);
	}
	
}
