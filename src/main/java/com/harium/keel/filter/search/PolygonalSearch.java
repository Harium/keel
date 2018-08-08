package com.harium.keel.filter.search;

import com.harium.etyl.geometry.Polygon;
import com.harium.keel.core.BooleanMaskSearch;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.PointFeature;

public abstract class PolygonalSearch extends BooleanMaskSearch {

    protected Polygon polygon;

    public PolygonalSearch(int w, int h) {
        super(w, h);

        polygon = new Polygon();
    }

    @Override
    public void setup(ImageSource source, PointFeature feature) {
        super.setup(source, feature);
        polygon.reset();
    }

    public Polygon getPolygon() {
        return polygon;
    }

}
