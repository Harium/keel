package com.harium.keel.core.effect;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Sorbel Filter
 * Code from: https://stackoverflow.com/a/30511674
 */
public class SorbelEffect implements Effect {

    /**
     * Expects a height mat as input
     *
     * @param input - A grayscale height map
     * @return edges
     */
    @Override
    public ImageSource apply(ImageSource input) {
        final int[][] pixelMatrix = new int[3][3];

        int w = input.getWidth();
        int h = input.getHeight();

        int[][] output = new int[h][w];

        for (int j = 1; j < h - 1; j++) {
            for (int i = 1; i < w - 1; i++) {
                pixelMatrix[0][0] = input.getR(i - 1, j - 1);
                pixelMatrix[0][1] = input.getRGB(i - 1, j);
                pixelMatrix[0][2] = input.getRGB(i - 1, j + 1);
                pixelMatrix[1][0] = input.getRGB(i, j - 1);
                pixelMatrix[1][2] = input.getRGB(i, j + 1);
                pixelMatrix[2][0] = input.getRGB(i + 1, j - 1);
                pixelMatrix[2][1] = input.getRGB(i + 1, j);
                pixelMatrix[2][2] = input.getRGB(i + 1, j + 1);

                int edge = (int) convolution(pixelMatrix);
                int rgb = (edge << 16 | edge << 8 | edge);
                output[j][i] = rgb;
            }
        }

        MatrixSource source = new MatrixSource(output);
        return source;
    }

    public static double convolution(int[][] pixelMatrix) {
        int gy = (pixelMatrix[0][0] * -1) + (pixelMatrix[0][1] * -2) + (pixelMatrix[0][2] * -1) + (pixelMatrix[2][0]) + (pixelMatrix[2][1] * 2) + (pixelMatrix[2][2] * 1);
        int gx = (pixelMatrix[0][0]) + (pixelMatrix[0][2] * -1) + (pixelMatrix[1][0] * 2) + (pixelMatrix[1][2] * -2) + (pixelMatrix[2][0]) + (pixelMatrix[2][2] * -1);

        return Math.sqrt(Math.pow(gy, 2) + Math.pow(gx, 2));
    }

}
