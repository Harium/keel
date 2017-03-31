package br.com.etyllica.keel.image.filter;

import br.com.etyllica.keel.core.helper.ColorHelper;
import br.com.etyllica.keel.image.ImageProcessor;

import java.awt.image.BufferedImage;

public class ContrastQuickFilter implements ImageProcessor {

    private int contrast = 10;

    public ContrastQuickFilter() {
        super();
    }

    public ContrastQuickFilter(int contrast) {
        super();

        this.contrast = contrast;
    }

    @Override
    public BufferedImage process(BufferedImage image) {

        int w = image.getWidth();

        int h = image.getHeight();

        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < h; j++) {

            for (int i = 0; i < w; i++) {

                int rgb = image.getRGB(i, j);

                int red = ColorHelper.getRed(rgb);

                int green = ColorHelper.getGreen(rgb);

                int blue = ColorHelper.getBlue(rgb);

                int average = (red + green + blue) / 3;

                //Bright pixel
                if (average > 128) {

                    red = brighterPixel(red);

                    green = brighterPixel(green);

                    blue = brighterPixel(blue);

                } else {

                    red = darkerPixel(red);

                    green = darkerPixel(green);

                    blue = darkerPixel(blue);

                }

                rgb = ColorHelper.getRGB(red, green, blue);

                output.setRGB(i, j, rgb);

            }

        }

        return output;
    }

    private int brighterPixel(int pixel) {

        if (pixel < 255 - contrast) {
            return pixel + contrast;
        } else {
            return 255;
        }

    }

    private int darkerPixel(int pixel) {

        if (pixel > contrast) {
            return pixel - contrast;
        } else {
            return 0;
        }

    }

}
