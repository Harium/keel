package com.harium.keel.effect.artistic;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.colormap.BlackWhite;
import com.harium.keel.colormap.ColorMap;
import com.harium.keel.core.Effect;
import com.harium.keel.core.source.ImageSource;

public class ColorizeEffect implements Effect {

    ColorMap colorMap = new BlackWhite();

    @Override
    public ImageSource apply(ImageSource feature) {
        for (int i = 0; i < feature.getHeight(); i++) {
            for (int j = 0; j < feature.getWidth(); j++) {
                int gray = feature.getGray(j, i);
                float value = gray / 255f;
                Color color = colorMap.getColor(value);
                feature.setRGB(j, i, color.getRGB());
            }
        }

        return feature;
    }

    public ColorizeEffect colorMap(ColorMap colorMap) {
        this.colorMap = colorMap;
        return this;
    }
}
