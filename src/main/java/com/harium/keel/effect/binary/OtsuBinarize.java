package com.harium.keel.effect.binary;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Otsu binarize filter serves to dynamically determine the threshold based on
 * the whole image and for later binarization on black (0) and white (255) pixels.
 * In determining threshold a image histogram is created in way that the value of
 * each pixel of image affects on the histogram appearance. Then, depending upon
 * the look of the histogram threshold counts and based on that, the real image
 * which is binarized is made.The image before this filter must be grayscale and
 * at the end image will contain only two colors - black and white.
 * <p>
 * reference to: http://zerocool.is-a-geek.net/?p=376
 * http://www.labbookpages.co.uk/software/imgProc/otsuThreshold.html
 *
 * @author Mihailo Stupar
 * <p>
 * Code from: https://github.com/neuroph/neuroph/blob/master/neuroph-2.9/ImageRec/src/main/java/org/neuroph/imgrec/filter/impl/OtsuBinarizeFilter.java
 */
public class OtsuBinarize implements Effect {

    @Override
    public ImageSource apply(ImageSource image) {

        MatrixSource originalImage = new MatrixSource(image);

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        MatrixSource filteredImage = new MatrixSource(image);

        int[] histogram = imageHistogram(originalImage);

        int totalNumberOfpixels = height * width;

        int threshold = threshold(histogram, totalNumberOfpixels);

        int black = 0;
        int white = 255;

        int alpha;
        int gray;
        int newColor;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gray = originalImage.getGray(i, j);
                alpha = originalImage.getA(i, j);

                if (gray > threshold)
                    newColor = white;
                else
                    newColor = black;

                newColor = ColorHelper.getARGB(newColor, newColor, newColor, alpha);
                filteredImage.setRGB(i, j, newColor);
            }
        }

        return filteredImage;
    }

    public static int[] imageHistogram(ImageSource image) {

        int[] histogram = new int[256];

        for (int i = 0; i < histogram.length; i++)
            histogram[i] = 0;

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                int gray = image.getB(i, j);
                histogram[gray]++;
            }
        }
        return histogram;
    }

    public static int threshold(int[] histogram, int total) {
        float sum = 0;
        for (int i = 0; i < 256; i++)
            sum += i * histogram[i];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0)
                continue;
            wF = total - wB;

            if (wF == 0)
                break;

            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }

}