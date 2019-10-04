package com.harium.keel.colormap;

import com.harium.etyl.commons.graphics.Color;

public interface ColorMap {

    /**
     * Receives a value and returns a color
     *
     * @param value float value between 0 and 1
     * @return Color
     */
    Color getColor(float value);

}
