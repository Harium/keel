package com.harium.keel.filter.color;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.etyl.commons.graphics.Color;

public class DarkerColorStrategy extends RGBColorStrategy {

	public DarkerColorStrategy(Color color, int tolerance) {
		super(color, tolerance);		
	}
	
	@Override
	public boolean validateColor(int rgb, int j, int i) {
		return ColorHelper.isDarkerColor(rgb, this.color, this.minToleranceRed, this.minToleranceGreen, this.minToleranceBlue);
	}
		
}
