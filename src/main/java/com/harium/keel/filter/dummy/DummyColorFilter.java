package com.harium.keel.filter.dummy;

import com.harium.keel.core.strategy.SelectionStrategy;

public class DummyColorFilter implements SelectionStrategy {

	@Override
	public boolean validateColor(int rgb, int j, int i) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean softValidateColor(int baseColor, int j, int i, int rgb) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
