package com.harium.keel.filter;

import com.harium.keel.core.strategy.PixelStrategy;

public interface SoftPixelStrategy extends PixelStrategy {
	public boolean strongValidateColor(int baseColor, int rgb, int j, int i);
}
