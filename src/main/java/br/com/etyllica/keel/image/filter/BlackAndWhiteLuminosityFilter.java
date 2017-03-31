package br.com.etyllica.keel.image.filter;

import br.com.etyllica.keel.core.helper.ColorHelper;
import br.com.etyllica.keel.image.ImageProcessor;

import java.awt.image.BufferedImage;

/**
 * Original ideas found at: https://www.johndcook.com/blog/2009/08/24/algorithms-convert-color-grayscale/
 */

public class BlackAndWhiteLuminosityFilter implements ImageProcessor {

    @Override
    public BufferedImage process(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int rgb = image.getRGB(j, i);
                int gray = toBlackAndWhite(rgb);

                rgb = ColorHelper.getRGB(gray, gray, gray);

                output.setRGB(j, i, rgb);
            }
        }

        return output;
    }

    public static int toBlackAndWhite(int rgb) {
        float red = ColorHelper.getRed(rgb) * 0.21f;
        float green = ColorHelper.getGreen(rgb) * 0.72f;
        float blue = ColorHelper.getBlue(rgb) * 0.07f;

        int gray = (int) (red + green + blue);
        return gray;
    }

}
