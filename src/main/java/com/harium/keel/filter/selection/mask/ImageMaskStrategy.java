package com.harium.keel.filter.selection.mask;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.filter.selection.RGBToleranceStrategy;

public class ImageMaskStrategy extends RGBToleranceStrategy {

	private ImageSource mask;

	public ImageMaskStrategy() {
		super();
	}

	public ImageMaskStrategy(ImageSource sourceMask) {
		this.mask = sourceMask;
	}

	public ImageMaskStrategy(ImageSource sourceMask, int tolerance) {
		this(sourceMask);
		setTolerance(tolerance);
	}

	@Override
	public boolean valid(int rgb, int j, int i) {
		if (mask == null) {
			return false;
		}
		int color = mask.getRGB(j, i);
		
		return !ColorHelper.isColor(rgb, color, minToleranceRed, maxToleranceRed,
				minToleranceGreen, maxToleranceGreen, minToleranceBlue, maxToleranceBlue);
	}

	public ImageSource getMask() {
		return mask;
	}

	public void setMask(ImageSource mask) {
		this.mask = mask;
	}
	
}
