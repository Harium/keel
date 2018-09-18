package com.harium.keel.filter.search;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;

public class TriangularSearch extends BooleanMaskSearch {

    public TriangularSearch(int w, int h) {
        super(w, h);
    }

    @Override
    public boolean filterFirst(int x, int y, int width, int height, ImageSource source, Feature component) {
        if (!mask[x][y] && selectionStrategy.validateColor(source.getRGB(x, y), x, y)) {
            int lwidth = findHorizontalLimit(source, x, y, w);
            int lheight = findVerticalLimit(source, x, y, h);

            lastComponent.setBounds(x, y, lwidth, lheight);

            return true;
        }

        return false;
    }

    @Override
    public boolean filter(int x, int y, int width, int height, ImageSource source, Feature component) {
        if (!mask[x][y] && selectionStrategy.validateColor(source.getRGB(x, y), x, y)) {
            int lwidth = findHorizontalLimit(source, x, y, w);
            int lheight = findVerticalLimit(source, x, y, h);

            PointFeature holder = new PointFeature(x, y, x + lwidth, y + lheight);
            updateMask(x, y, lwidth, lheight, true);

            results.add(holder);
        }

        return false;
    }

    private int findHorizontalLimit(ImageSource bimg, int i, int j, int w) {

        int totalWidth = 0;

        for (int ni = i; ni < w; ni++) {

            if (!mask[ni][j] && selectionStrategy.validateColor(bimg.getRGB(ni, j), ni, j)) {
                totalWidth++;
            } else {
                break;
            }

        }

        return totalWidth;
    }

    private int findVerticalLimit(ImageSource bimg, int i, int j, int h) {

        int totalHeight = 0;

        for (int nj = j; nj < h; nj++) {

            if (!mask[i][nj] && selectionStrategy.validateColor(bimg.getRGB(i, nj), i, nj)) {
                totalHeight++;
            } else {
                break;
            }

        }

        return totalHeight;
    }

}
