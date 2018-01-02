package com.harium.keel.filter.search;

import com.harium.etyl.linear.Polygon;
import com.harium.keel.core.BooleanMaskSearch;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;

public abstract class PolygonalSearch extends BooleanMaskSearch {

    protected Polygon polygon;

    public PolygonalSearch(int w, int h) {
        super(w, h);

        polygon = new Polygon();
    }

    @Override
    public void setup(ImageSource source, Component component) {
        super.setup(source, component);
        polygon.reset();
    }

    public Polygon getPolygon() {
        return polygon;
    }

}
