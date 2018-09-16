package com.harium.keel.core.source;

public interface ImageSource {

    int getWidth();

    int getHeight();

    int getR(int x, int y);

    int getG(int x, int y);

    int getB(int x, int y);

    int getRGB(int x, int y);

    void setRGB(int x, int y, int rgb);

}
