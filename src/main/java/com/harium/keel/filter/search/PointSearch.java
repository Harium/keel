package com.harium.keel.filter.search;

import com.harium.keel.core.BooleanMaskSearch;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

public class PointSearch extends BooleanMaskSearch {

    public PointSearch(int w, int h) {
        super(w, h);
    }

    @Override
    public boolean filter(int x, int y, int width, int height, ImageSource source, Feature component) {
        if (!mask[x][y] && selectionStrategy.validateColor(source.getRGB(x, y), x, y)) {
            PointFeature holder = new PointFeature(x, y, 1, 1);
            results.add(holder);
            return true;
        }
        return false;
    }

    @Override
    public boolean filterFirst(int x, int y, int width, int height, ImageSource source, Feature component) {
        if (!mask[x][y] && selectionStrategy.validateColor(source.getRGB(x, y), x, y)) {
            lastComponent.setBounds(x, y, 1, 1);
            return true;

        }
        return false;
    }
}
