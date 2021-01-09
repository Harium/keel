package com.harium.keel.processor;

import com.harium.keel.core.Processor;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

public class MatchProcessor implements Processor<Boolean> {

    private int x = 0;
    private int y = 0;
    private ImageSource mask;

    @Override
    public Boolean apply(ImageSource feature) {
        for (int i = 0; i < feature.getHeight() - mask.getHeight(); i++) {
            for (int j = 0; j < feature.getWidth() - mask.getWidth(); j++) {
                int rgb = feature.getRGB(j, i);
                int crgb = mask.getRGB(j, i);
                if (!ColorHelper.isColor(rgb, crgb)) {
                    return false;
                }
            }
        }

        return true;
    }

    public MatchProcessor mask(ImageSource mask) {
        this.mask = mask;
        return this;
    }

    public MatchProcessor x(int x) {
        this.x = x;
        return this;
    }

    public MatchProcessor y(int y) {
        this.y = y;
        return this;
    }
}
