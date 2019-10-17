package com.harium.keel.filter.selection;

import com.harium.keel.core.strategy.SelectionStrategy;

public class DummySelectionStrategy implements SelectionStrategy {

	@Override
	public boolean valid(int rgb, int x, int y) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setBaseRGB(int baseRGB) {

	}

	@Override
	public void setStrength(float strength) {

	}

	@Override
	public void setSoftSelection(boolean softSelection) {

	}

}
