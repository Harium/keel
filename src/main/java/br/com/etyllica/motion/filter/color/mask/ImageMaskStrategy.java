package br.com.etyllica.motion.filter.color.mask;

import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.ToleranceStrategy;

public class ImageMaskStrategy extends ToleranceStrategy {

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
	public boolean validateColor(int rgb, int j, int i) {
		if (mask == null) {
			return false;
		}
		int color = mask.getRGB(j, i);
		
		return ColorStrategy.isColor(rgb, color, minToleranceRed, maxToleranceRed, 
				minToleranceGreen, maxToleranceGreen, minToleranceBlue, maxToleranceBlue);
	}

	public ImageSource getMask() {
		return mask;
	}

	public void setMask(ImageSource mask) {
		this.mask = mask;
	}
	
}
