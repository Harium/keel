package com.harium.keel.effect.resize;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Nearest Neighbor Resize Effect
 * This is a fast way to resize an image
 *
 * http://tech-algorithm.com/articles/nearest-neighbor-image-scaling/
 */
public class NearestNeighborResizeEffect extends ResizeEffect {

    @Override
    public ImageSource apply(ImageSource input) {
        MatrixSource out = new MatrixSource(width, height);

        int xRatio = ((input.getWidth() << 16) / width) + 1;
        int yRatio = ((input.getHeight() << 16) / height) + 1;

        int x2, y2;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                x2 = ((i * xRatio) >> 16);
                y2 = ((j * yRatio) >> 16);

                int rgb = input.getRGB(x2, y2);
                out.setRGB(i, j, rgb);
            }
        }
        return out;
    }
}
