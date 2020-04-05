package com.harium.keel.effect;

import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

import java.util.ArrayList;

/**
 * Code from: https://github.com/schauhan19/Histogram-Equalization
 */
public class HistogramEqualization implements Effect {

    public static final int LUT_SIZE = 256;

    @Override
    public ImageSource apply(ImageSource original) {
        int red;
        int green;
        int blue;
        int alpha;
        int newPixel = 0;

        // Get the Lookup table for histogram equalization
        ArrayList<int[]> histLUT = histogramEqualizationLUT(original);

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {

                // Get pixels by R, G, B
                int rgb = original.getRGB(i, j);
                alpha = ColorHelper.getAlpha(rgb);
                red = ColorHelper.getRed(rgb);
                green = ColorHelper.getGreen(rgb);
                blue = ColorHelper.getBlue(rgb);

                // Set new pixel values using the histogram lookup table
                red = histLUT.get(0)[red];
                green = histLUT.get(1)[green];
                blue = histLUT.get(2)[blue];

                // Return back to original format
                newPixel = ColorHelper.getARGB(red, green, blue, alpha);

                // Write pixels into image
                original.setRGB(i, j, newPixel);
            }
        }

        return original;

    }

    // Get the histogram equalization lookup table for separate R, G, B channels
    private static ArrayList<int[]> histogramEqualizationLUT(ImageSource input) {

        // Get an image histogram - calculated values by R, G, B channels
        ArrayList<int[]> imageHist = imageHistogram(input);

        // Create the lookup table
        ArrayList<int[]> imageLUT = new ArrayList<>();

        // Fill the lookup table
        int[] rhistogram = new int[LUT_SIZE];
        int[] ghistogram = new int[LUT_SIZE];
        int[] bhistogram = new int[LUT_SIZE];

        long sumr = 0;
        long sumg = 0;
        long sumb = 0;

        // Calculate the scale factor
        float scale_factor = (float) (255.0 / (input.getWidth() * input.getHeight()));

        for (int i = 0; i < LUT_SIZE; i++) {
            sumr += imageHist.get(0)[i];
            int valr = (int) (sumr * scale_factor);
            if (valr > 255) {
                rhistogram[i] = 255;
            } else rhistogram[i] = valr;

            sumg += imageHist.get(1)[i];
            int valg = (int) (sumg * scale_factor);
            if (valg > 255) {
                ghistogram[i] = 255;
            } else ghistogram[i] = valg;

            sumb += imageHist.get(2)[i];
            int valb = (int) (sumb * scale_factor);
            if (valb > 255) {
                bhistogram[i] = 255;
            } else bhistogram[i] = valb;
        }

        imageLUT.add(rhistogram);
        imageLUT.add(ghistogram);
        imageLUT.add(bhistogram);

        return imageLUT;
    }

    // Return an ArrayList containing histogram values for separate R, G, B channels
    public static ArrayList<int[]> imageHistogram(ImageSource input) {

        int[] rhistogram = new int[LUT_SIZE];
        int[] ghistogram = new int[LUT_SIZE];
        int[] bhistogram = new int[LUT_SIZE];

        for (int i = 0; i < rhistogram.length; i++) {
            rhistogram[i] = 0;
            ghistogram[i] = 0;
            bhistogram[i] = 0;
        }

        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {

                int rgb = input.getRGB(x, y);

                int red = ColorHelper.getRed(rgb);
                int green = ColorHelper.getGreen(rgb);
                int blue = ColorHelper.getBlue(rgb);

                // Increase the values of colors
                rhistogram[red]++;
                ghistogram[green]++;
                bhistogram[blue]++;
            }
        }

        ArrayList<int[]> hist = new ArrayList<>();
        hist.add(rhistogram);
        hist.add(ghistogram);
        hist.add(bhistogram);

        return hist;

    }

}
