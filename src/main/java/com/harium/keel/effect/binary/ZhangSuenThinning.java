package com.harium.keel.effect.binary;

import com.harium.etyl.geometry.math.Vector2i;
import com.harium.keel.core.Effect;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nayef on 1/26/15.
 * Source from: https://nayefreza.wordpress.com/2013/05/11/zhang-suen-thinning-algorithm-java-implementation/
 */
public class ZhangSuenThinning implements Effect {

    private int border = 1;
    private static final int FOREGROUND = 255;
    private static final int BACKGROUND = 0;
    private static final int BACKGROUND_COLOR = ColorHelper.getRGB(0, 0, 0);

    /**
     * @param input
     * @return a 2D array of binary image after thinning using zhang-suen thinning algo.
     */
    @Override
    public ImageSource apply(ImageSource input) {

        int a, b;
        List<Vector2i> pointsToChange = new LinkedList<>();
        boolean hasChange = false;


        do {
            for (int y = border; y < input.getHeight() - border; y++) {
                for (int x = border; x < input.getWidth() - border; x++) {
                    a = getA(input, x, y);
                    b = getB(input, x, y);
                    if (input.getGray(x, y) == FOREGROUND && 2 <= b && b <= 6 && a == FOREGROUND
                            && (input.getGray(x, y - 1) == BACKGROUND || input.getGray(x + 1, y) == BACKGROUND || input.getGray(x, y + 1) == BACKGROUND)
                            && (input.getGray(x + 1, y) == BACKGROUND || input.getGray(x, y + 1) == BACKGROUND || input.getGray(x - 1, y) == BACKGROUND)) {

                        pointsToChange.add(new Vector2i(x, y));
                        hasChange = true;
                    }
                }
            }

            for (Vector2i point : pointsToChange) {
                //input.setRGB(point.getX(), point.getY(), BACKGROUND_COLOR);
                input.setGray(point.x, point.y, BACKGROUND);
            }
            pointsToChange.clear();

            for (int y = border; y < input.getHeight() - border; y++) {
                for (int x = border; x < input.getWidth() - border; x++) {
                    a = getA(input, x, y);
                    b = getB(input, x, y);
                    if (input.getRGB(x, y) == FOREGROUND && 2 <= b && b <= 6 && a == FOREGROUND
                            && (input.getGray(x, y - 1) == BACKGROUND || input.getGray(x + 1, y) == BACKGROUND || input.getGray(x - 1, y) == BACKGROUND)
                            && (input.getGray(x, y - 1) == BACKGROUND || input.getGray(x, y + 1) == BACKGROUND || input.getGray(x - 1, y) == BACKGROUND)) {

                        pointsToChange.add(new Vector2i(x, y));
                        hasChange = true;
                    }
                }
            }

            for (Vector2i point : pointsToChange) {
                //input.setRGB(point.getX(), point.getY(), BACKGROUND_COLOR);
                input.setRGB(point.x, point.y, BACKGROUND);
            }
            pointsToChange.clear();
        } while (hasChange);


        return input;
    }

    private int getA(ImageSource binaryImage, int x, int y) {
        int count = 0;
        //p2 p3
        if (x - 1 >= 0 && y + 1 < binaryImage.getWidth() && binaryImage.getRGB(x - 1, y) == BACKGROUND && binaryImage.getRGB(x - 1, y + 1) == FOREGROUND) {
            count++;
        }
        //p3 p4
        if (x - 1 >= 0 && y + 1 < binaryImage.getWidth() && binaryImage.getRGB(x - 1, y + 1) == BACKGROUND && binaryImage.getRGB(x, y + 1) == FOREGROUND) {
            count++;
        }
        //p4 p5
        if (x + 1 < binaryImage.getHeight() && y + 1 < binaryImage.getWidth() && binaryImage.getRGB(x, y + 1) == BACKGROUND && binaryImage.getRGB(x + 1, y + 1) == FOREGROUND) {
            count++;
        }
        //p5 p6
        if (x + 1 < binaryImage.getHeight() && y + 1 < binaryImage.getWidth() && binaryImage.getRGB(x + 1, y + 1) == BACKGROUND && binaryImage.getRGB(x + 1, y) == FOREGROUND) {
            count++;
        }
        //p6 p7
        if (x + 1 < binaryImage.getHeight() && y - 1 >= 0 && binaryImage.getRGB(x + 1, y) == BACKGROUND && binaryImage.getRGB(x + 1, y - 1) == FOREGROUND) {
            count++;
        }
        //p7 p8
        if (x + 1 < binaryImage.getHeight() && y - 1 >= 0 && binaryImage.getRGB(x + 1, y - 1) == BACKGROUND && binaryImage.getRGB(x, y - 1) == FOREGROUND) {
            count++;
        }
        //p8 p9
        if (x - 1 >= 0 && y - 1 >= 0 && binaryImage.getRGB(x, y - 1) == BACKGROUND && binaryImage.getRGB(x - 1, y - 1) == FOREGROUND) {
            count++;
        }
        //p9 p2
        if (x - 1 >= 0 && y - 1 >= 0 && binaryImage.getRGB(x - 1, y - 1) == BACKGROUND && binaryImage.getRGB(x - 1, y) == FOREGROUND) {
            count++;
        }
        return count;
    }

    private int getB(ImageSource binaryImage, int x, int y) {
        int count = 0;
        if (isForeground(binaryImage.getRGB(x - 1, y))) {
            count++;
        }
        if (isForeground(binaryImage.getRGB(x - 1, y + 1))) {
            count++;
        }
        if (isForeground(binaryImage.getRGB(x, y + 1))) {
            count++;
        }
        if (isForeground(binaryImage.getRGB(x + 1, y + 1))) {
            count++;
        }
        if (isForeground(binaryImage.getRGB(x + 1, y))) {
            count++;
        }
        if (isForeground(binaryImage.getRGB(x + 1, y - 1))) {
            count++;
        }
        if (isForeground(binaryImage.getRGB(x, y - 1))) {
            count++;
        }
        if (isForeground(binaryImage.getRGB(x - 1, y - 1))) {
            count++;
        }

        return count;
    }

    private static boolean isForeground(int rgb) {
        return rgb == FOREGROUND;
    }
}
