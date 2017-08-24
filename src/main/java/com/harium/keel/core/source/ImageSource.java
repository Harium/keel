package com.harium.keel.core.source;

public interface ImageSource {

    int getWidth();

    int getHeight();

    int getRGB(int x, int y);
}
