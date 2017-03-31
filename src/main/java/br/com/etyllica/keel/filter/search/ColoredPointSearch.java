package br.com.etyllica.keel.filter.search;

import br.com.etyllica.keel.core.BooleanMaskSearch;
import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.color.ColorStrategy;

import java.awt.*;
import java.util.List;

public class ColoredPointSearch extends BooleanMaskSearch {

    public ColoredPointSearch(int w, int h) {
        super(w, h);
    }

    public ColoredPointSearch(int w, int h, Color color) {
        super(w, h, new ColorStrategy(color.getRGB()));
    }

    @Override
    public Component filterFirst(ImageSource bimg, Component component) {
        super.setup(component.getW(), component.getH());

        int x = component.getLowestX() + border;
        int y = component.getLowestY() + border;

        int w = component.getW() - border;
        int h = component.getH() - border;

        for (int j = y; j < h; j += step) {

            for (int i = x; i < w; i += step) {

                if (!mask[i][j] && pixelStrategy.validateColor(bimg.getRGB(i, j), i, j)) {

                    lastComponent.setBounds(i, j, 1, 1);

                    return lastComponent;

                }

            }

        }

        return lastComponent;

    }

    @Override
    public List<Component> filter(ImageSource bimg, Component component) {
        super.setup(component.getW(), component.getH());

        int x = component.getLowestX() + border;
        int y = component.getLowestY() + border;

        int w = component.getW() - border;
        int h = component.getH() - border;

        //TODO Swap i,j
        for (int j = y; j < h; j += step) {

            for (int i = x; i < w; i += step) {

                if (!maskStrategy.validateMask(i, j)) {
                    continue;
                }

                if (!mask[i][j] && pixelStrategy.validateColor(bimg.getRGB(i, j), i, j)) {

                    Component holder = new Component(i, j, 1, 1);

                    result.add(holder);

                    return result;
                }

            }

        }

        return result;
    }

}
