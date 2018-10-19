package com.harium.keel.core.source;

public interface ImageSource {

    int getWidth();

    int getHeight();

    int getA(int x, int y);

    int getR(int x, int y);

    int getG(int x, int y);

    int getB(int x, int y);

    int getGray(int x, int y);

    void setGray(int x, int y, int gray);

    int getRGB(int x, int y);

    void setRGB(int x, int y, int rgb);

    void setRGB(int x, int y, int rgb, int alpha);

    boolean isGrayscale();

    void setGrayscale(boolean grayscale);

}
