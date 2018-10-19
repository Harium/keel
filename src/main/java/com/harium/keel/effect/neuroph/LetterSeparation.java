package com.harium.keel.effect.neuroph;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import com.harium.keel.effect.binary.OtsuBinarize;

/**
 * Prvo izrcunav threshold na osnovu otsu i binrzije
 * zatim na osnu bw i originalne slike podesaava(spusta) threshold tako da slova ne budu spojena
 *
 * @author Mihailo Stupar
 * <p>
 * Code from: https://github.com/neuroph/neuroph/blob/master/neuroph-2.9/ImageRec/src/main/java/org/neuroph/imgrec/filter/impl/LetterSeparationFilter.java
 */
public class LetterSeparation implements Effect {

    /**
     * radi otsu da dobije spojena crna slova i ra
     *
     * @param input
     * @return the processed image
     */
    public ImageSource apply(ImageSource input) {
        ImageSource originalImage = input;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        boolean[][] matrix = new boolean[width][height]; // black n white boolean matrix; true = blck, false = white

        // Copy
        ImageSource filteredImage = new MatrixSource(input);

        int[] histogram = OtsuBinarize.imageHistogram(originalImage);

        int totalNumberOfpixels = height * width;

        int threshold = OtsuBinarize.threshold(histogram, totalNumberOfpixels);

        int black = 0;
        int white = 255;

        int gray;
        int alpha;
        int newColor;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gray = originalImage.getGray(i, j);

                if (gray > threshold) {
                    matrix[i][j] = false;
                } else {
                    matrix[i][j] = true;
                }

            }
        }

        int blackTreshold = letterThreshold(originalImage, matrix);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gray = originalImage.getGray(i, j);
                alpha = originalImage.getA(i, j);

                if (gray > blackTreshold) {
                    newColor = white;
                } else {
                    newColor = black;
                }

                newColor = ColorHelper.getARGB(newColor, newColor, newColor, alpha);
                filteredImage.setRGB(i, j, newColor);
            }
        }

        return filteredImage;
    }

    public int letterThreshold(ImageSource original, boolean[][] matrix) {
        double sum = 0;
        int count = 0;

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                if (matrix[i][j]) {
                    int gray = original.getGray(i, j);
                    sum += gray;
                    count++;
                }
            }
        }

        if (count == 0) {
            return 0;
        }

        return (int) Math.round((sum * 3) / (count * 2)); // 3 i 2 su plinkove konstnte
    }

    // Threshold by otsu method
    /*private int threshold(int[] histogram, int total) {
        float sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0) {
                continue;
            }
            wF = total - wB;

            if (wF == 0) {
                break;
            }

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
    }*/

}