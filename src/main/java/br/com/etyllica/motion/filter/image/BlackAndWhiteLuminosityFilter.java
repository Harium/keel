package br.com.etyllica.motion.filter.image;

import java.awt.image.BufferedImage;

import br.com.etyllica.motion.filter.color.ColorStrategy;

/**
 * Original ideas found at: https://www.johndcook.com/blog/2009/08/24/algorithms-convert-color-grayscale/
 *
 */

public class BlackAndWhiteLuminosityFilter implements ImageProcessor {

	@Override
	public BufferedImage process(BufferedImage image) {
		int w = image.getWidth();		
		int h = image.getHeight();
		
		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				int rgb = image.getRGB(j, i);
				int gray = toBlackAndWhite(rgb);
				
				rgb = ColorStrategy.getRGB(gray, gray, gray);
								
				output.setRGB(j, i, rgb);
			}
		}
		
		return output;
	}
	
	public static int toBlackAndWhite(int rgb) {
		float red = ColorStrategy.getRed(rgb) * 0.21f;		
		float green = ColorStrategy.getGreen(rgb) * 0.72f;		
		float blue = ColorStrategy.getBlue(rgb) * 0.07f;
		
		int gray = (int)(red + green + blue);
		return gray;
	}
	
}
