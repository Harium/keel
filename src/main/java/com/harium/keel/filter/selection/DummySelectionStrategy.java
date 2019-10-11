package com.harium.keel.filter.selection;

import com.harium.keel.core.strategy.SelectionStrategy;

public class DummySelectionStrategy implements SelectionStrategy {

	@Override
	public boolean valid(int rgb, int j, int i) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setBaseRGB(int baseRGB) {

	}

	@Override
	public void setStrength(float strength) {

	}

}
