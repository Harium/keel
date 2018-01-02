package com.harium.keel.filter.search;

import com.harium.keel.core.BooleanMaskSearch;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;

public class PointSearch extends BooleanMaskSearch {

    public PointSearch(int w, int h) {
        super(w, h);
    }

    @Override
    public boolean filter(int x, int y, int width, int height, ImageSource source, Component component) {
        if (!mask[x][y] && pixelStrategy.validateColor(source.getRGB(x, y), x, y)) {
            Component holder = new Component(x, y, 1, 1);
            results.add(holder);
            return true;
        }
        return false;
    }

    @Override
    public boolean filterFirst(int x, int y, int width, int height, ImageSource source, Component component) {
        if (!mask[x][y] && pixelStrategy.validateColor(source.getRGB(x, y), x, y)) {
            lastComponent.setBounds(x, y, 1, 1);
            return true;

        }
        return false;
    }
}
