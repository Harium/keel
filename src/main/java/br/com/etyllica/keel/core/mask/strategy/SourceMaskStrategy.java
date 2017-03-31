package br.com.etyllica.keel.core.mask.strategy;

import br.com.etyllica.keel.core.helper.ColorHelper;
import br.com.etyllica.keel.core.mask.DynamicPixel;
import br.com.etyllica.keel.core.source.ImageSource;

public class SourceMaskStrategy implements DynamicMaskStrategy {

    ImageSource mask;
    ImageSource source;

    int minToleranceRed;
    int maxToleranceRed;
    int minToleranceGreen;
    int maxToleranceGreen;
    int minToleranceBlue;
    int maxToleranceBlue;

    public SourceMaskStrategy(ImageSource mask) {
        this.mask = mask;
        this.source = mask;
    }

    public void reset(int[][] mask) {
        EmptyMaskStrategy.initMask(mask);
    }

    public void update(ImageSource source, int[][] mask) {
        int w = source.getWidth();
        int h = source.getHeight();

        for (int nj = 0; nj < h; nj++) {
            for (int ni = 0; ni < w; ni++) {
                if (compareSources(ni, nj)) {
                    mask[ni][nj] = DynamicPixel.INVALID;
                } else {
                	mask[ni][nj] = DynamicPixel.UNKNOWN;
                }
            }
        }
    }

    private boolean compareSources(int x, int y) {

        int maskColor = mask.getRGB(x, y);
        int currentSourceColor = mask.getRGB(x, y);

        return !ColorHelper.isColor(maskColor, currentSourceColor, minToleranceRed, maxToleranceRed,
                minToleranceGreen, maxToleranceGreen, minToleranceBlue, maxToleranceBlue);
    }

    public void setSource(ImageSource source) {
        this.source = source;
    }

    public int getMinToleranceRed() {
        return minToleranceRed;
    }

    public void setMinToleranceRed(int minToleranceRed) {
        this.minToleranceRed = minToleranceRed;
    }

    public int getMaxToleranceRed() {
        return maxToleranceRed;
    }

    public void setMaxToleranceRed(int maxToleranceRed) {
        this.maxToleranceRed = maxToleranceRed;
    }

    public int getMinToleranceGreen() {
        return minToleranceGreen;
    }

    public void setMinToleranceGreen(int minToleranceGreen) {
        this.minToleranceGreen = minToleranceGreen;
    }

    public int getMaxToleranceGreen() {
        return maxToleranceGreen;
    }

    public void setMaxToleranceGreen(int maxToleranceGreen) {
        this.maxToleranceGreen = maxToleranceGreen;
    }

    public int getMinToleranceBlue() {
        return minToleranceBlue;
    }

    public void setMinToleranceBlue(int minToleranceBlue) {
        this.minToleranceBlue = minToleranceBlue;
    }

    public int getMaxToleranceBlue() {
        return maxToleranceBlue;
    }

    public void setMaxToleranceBlue(int maxToleranceBlue) {
        this.maxToleranceBlue = maxToleranceBlue;
    }
}
