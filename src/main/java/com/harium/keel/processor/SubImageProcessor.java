package com.harium.keel.processor;

import com.harium.keel.core.Processor;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;

public class SubImageProcessor implements Processor<int[]> {

    private final MatchProcessor matchProcessor = new MatchProcessor();

    private ImageSource mask;

    @Override
    public int[] apply(ImageSource feature) {
        for (int i = 0; i < feature.getHeight() - mask.getHeight(); i++) {
            for (int j = 0; j < feature.getWidth() - mask.getWidth(); j++) {
                int rgb = feature.getRGB(j, i);
                int crgb = mask.getRGB(j, i);
                // Roughly compare pixels
                if (preliminaryCheck(rgb, crgb)) {
                    // Compare all pixels
                    if (matchProcessor.x(j).y(i).apply(feature)) {
                        return new int[]{j, i};
                    }
                }
            }
        }

        return new int[2];
    }

    private boolean preliminaryCheck(int rgb, int crgb) {
        return ColorHelper.isColor(rgb, crgb);
    }

    public SubImageProcessor mask(ImageSource mask) {
        this.mask = mask;
        matchProcessor.mask(mask);
        return this;
    }
}
