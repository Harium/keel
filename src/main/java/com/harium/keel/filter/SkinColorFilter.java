package com.harium.keel.filter;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.custom.CustomFilter;
import com.harium.keel.feature.Feature;
import com.harium.keel.feature.PointFeature;
import com.harium.keel.filter.color.SimpleToleranceStrategy;
import com.harium.keel.filter.color.skin.SkinColorStrategy;
import com.harium.keel.filter.search.flood.SoftFloodFillSearch;

import java.util.List;

public class SkinColorFilter extends CustomFilter<PointFeature> {

    protected SimpleToleranceStrategy colorStrategy;

    public SkinColorFilter(int w, int h) {
        super(new SoftFloodFillSearch(w, h));
    }

    public SkinColorFilter(int w, int h, int tolerance) {
        this(w, h);

        colorStrategy = new SkinColorStrategy(tolerance);
        filter.setSelectionStrategy(colorStrategy);
    }

    public SkinColorFilter(int w, int h, SimpleToleranceStrategy colorStrategy) {
        this(w, h);

        this.colorStrategy = colorStrategy;
        filter.setSelectionStrategy(colorStrategy);
    }

    public PointFeature filterFirst(ImageSource source, Feature component) {
        return filter.filterFirst(source, component);
    }

    public List<PointFeature> filter(ImageSource source, Feature component) {
        return filter.filter(source, component);
    }

    public void setTolerance(int tolerance) {
        colorStrategy.setTolerance(tolerance);
    }

}
