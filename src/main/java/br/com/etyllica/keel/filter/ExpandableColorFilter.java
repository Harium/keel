package br.com.etyllica.keel.filter;

import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.search.flood.ExpandableFloodFillSearch;
import com.harium.etyl.commons.graphics.Color;

import java.util.List;

public class ExpandableColorFilter extends ColorPointFilter {

    protected int tolerance = 0x40;

    public ExpandableColorFilter(int w, int h) {
        this(w, h, Color.BLACK);
    }

    public ExpandableColorFilter(int w, int h, Color color) {
        super(w, h);

        this.searchStrategy = new ExpandableFloodFillSearch(w, h);
        colorStrategy = new ColorStrategy(color, tolerance);
        searchStrategy.setPixelStrategy(colorStrategy);
    }

    public ExpandableColorFilter(int w, int h, Color color, int tolerance) {
        this(w, h, color);
        colorStrategy.setTolerance(tolerance);
    }

    @Override
    public List<Component> filter(ImageSource bimg, Component component) {
        //Setup happens on filter
        //searchStrategy.setup();
        return searchStrategy.filter(bimg, component);
    }

    public ColorStrategy getColorStrategy() {
        return colorStrategy;
    }

}
