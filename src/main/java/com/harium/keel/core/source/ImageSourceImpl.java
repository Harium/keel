package com.harium.keel.core.source;

import com.harium.keel.core.helper.ColorHelper;

public abstract class ImageSourceImpl implements ImageSource {

    protected boolean grayscale = false;

    public ImageSourceImpl() {

    }

    public int getA(int x, int y) {
        return ColorHelper.getAlpha(getRGB(x, y));
    }

    public int getR(int x, int y) {
        return ColorHelper.getRed(getRGB(x, y));
    }

    public int getG(int x, int y) {
        return ColorHelper.getGreen(getRGB(x, y));
    }

    public int getB(int x, int y) {
        return ColorHelper.getBlue(getRGB(x, y));
    }

    @Override
    public int getGray(int x, int y) {
        return getB(x, y);
    }

    @Override
    public void setRGB(int x, int y, int rgb, int alpha) {
        int rgba = ColorHelper.getARGB(rgb, alpha);
        setRGB(x, y, rgba);
    }

    @Override
    public void setGray(int x, int y, int gray) {
        int rgb;
        if (isGrayscale()) {
            // TODO Should ignore Alpha?
            rgb = ColorHelper.getRGB(gray, gray, gray);
            setRGB(x, y, rgb);
        } else {
            int alpha = getA(x, y);

            if (alpha < 255) {
                rgb = ColorHelper.getARGB(gray, gray, gray, alpha);
            } else {
                rgb = ColorHelper.getRGB(gray, gray, gray);
            }
            setRGB(x, y, rgb);
        }
    }

    public boolean isGrayscale() {
        return grayscale;
    }

    public void setGrayscale(boolean grayscale) {
        this.grayscale = grayscale;
    }
}
