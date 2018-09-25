package com.harium.keel.effect.helper;

import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

public class EffectHelper {

    // No instances allowed
    private EffectHelper() {

    }

    public static void setRGB(int index, int rgb, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);
        input.setRGB(x, y, rgb);
    }

    public static void setRGB(int index, int r, int g, int b, ImageSource input) {
        int rgb = ColorHelper.getRGB(r, g, b);
        int x = getX(index, input);
        int y = getY(index, input);
        input.setRGB(x, y, rgb);
    }

    public static void setRGB(int index, int r, int g, int b, int a, ImageSource input) {
        int rgb = ColorHelper.getARGB(r, g, b, a);
        int x = getX(index, input);
        int y = getY(index, input);
        input.setRGB(x, y, rgb);
    }

    public static void setRed(int index, int red, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);

        int rgb = input.getRGB(x, y);

        int r = red;
        int g = ColorHelper.getRed(rgb);
        int b = ColorHelper.getBlue(rgb);

        input.setRGB(x, y, ColorHelper.getRGB(r, g, b));
    }

    public static void setGreen(int index, int green, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);
        int rgb = input.getRGB(x, y);

        int r = ColorHelper.getRed(rgb);
        int g = green;
        int b = ColorHelper.getBlue(rgb);

        input.setRGB(x, y, ColorHelper.getRGB(r, g, b));
    }

    public static void setBlue(int index, int blue, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);
        int rgb = input.getRGB(x, y);

        int r = ColorHelper.getRed(rgb);
        int g = ColorHelper.getGreen(rgb);
        int b = blue;

        input.setRGB(x, y, ColorHelper.getRGB(r, g, b));
    }

    public static void setGray(int index, int gray, ImageSource input) {
        setRGB(index, gray, input);
    }

    public static int getRed(int index, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);
        return input.getR(x, y);
    }

    public static int getGreen(int index, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);
        return input.getG(x, y);
    }

    public static int getBlue(int index, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);
        return input.getB(x, y);
    }

    public static int getAlpha(int index, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);
        return input.getA(x, y);
    }

    public static int getRGB(int index, ImageSource input) {
        int x = getX(index, input);
        int y = getY(index, input);
        return input.getRGB(x, y);
    }

    public static int getGray(int index, ImageSource input) {
        return getRGB(index, input);
    }

    public static int getY(int index, ImageSource overlay) {
        return index / overlay.getWidth();
    }

    public static int getX(int index, ImageSource overlay) {
        return index % overlay.getWidth();
    }

    public static int getSize(ImageSource input) {
        return input.getWidth() * input.getHeight();
    }

    public static void setImage(ImageSource source, ImageSource destination) {
        for (int i = 0; i < destination.getHeight(); i++) {
            for (int j = 0; j < destination.getWidth(); j++) {
                destination.setRGB(j, i, source.getRGB(j, i));
            }
        }
    }
}
