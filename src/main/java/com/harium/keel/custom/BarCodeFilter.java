package com.harium.keel.custom;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.filter.color.RGBColorStrategy;
import com.harium.keel.filter.search.flood.FloodFillSearch;
import com.harium.keel.modifier.EnvelopeModifier;

public class BarCodeFilter extends CustomFilter {

    public BarCodeFilter(int w, int h) {
        super();

        RGBColorStrategy pixelStrategy = new RGBColorStrategy(Color.BLACK);
        modifierStrategy = new EnvelopeModifier();

        filter = new FloodFillSearch(w, h);
        filter.setSelectionStrategy(pixelStrategy);
        filter.setComponentModifierStrategy(modifierStrategy);
    }

}
