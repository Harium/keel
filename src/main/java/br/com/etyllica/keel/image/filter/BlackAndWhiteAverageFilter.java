package br.com.etyllica.keel.image.filter;

import java.awt.image.BufferedImage;

import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.image.ImageProcessor;

/**
 * Original ideas found at: http://www.tannerhelland.com/3643/grayscale-image-algorithm-vb6/
 *
 */

public class BlackAndWhiteAverageFilter implements ImageProcessor {

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
		int red = ColorStrategy.getRed(rgb);		
		int green = ColorStrategy.getGreen(rgb);		
		int blue = ColorStrategy.getBlue(rgb);
		
		int gray = (red + green + blue)/3;
		return gray;
	}
	
}
