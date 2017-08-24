package com.harium.keel.image.filter;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.image.ImageProcessor;

import java.awt.image.BufferedImage;

/**
 * Original ideas found at: http://www.tannerhelland.com/3643/grayscale-image-algorithm-vb6/
 */

public class BlackAndWhiteAverageFilter implements ImageProcessor {

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
        int red = ColorHelper.getRed(rgb);
        int green = ColorHelper.getGreen(rgb);
        int blue = ColorHelper.getBlue(rgb);

        int gray = (red + green + blue) / 3;
        return gray;
    }

}
