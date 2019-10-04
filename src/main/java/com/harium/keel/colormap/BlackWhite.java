package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public class BlackWhite implements ColorMap {

    @Override
    public Color getColor(float value) {
        int gray = (int) (255 * value);
        Color color = new Color(gray, gray, gray);
        return color;
    }

}
