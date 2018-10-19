package com.harium.keel.effect.normal;

import com.badlogic.gdx.math.Vector3;
import com.harium.keel.core.helper.VectorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Reference: http://www.alejandrosegovia.net/2014/03/31/bump-map-generation/
 */
public class SobelNormalMap extends NormalMap {

    /**
     * Sobel method to generate bump map from a height map
     *
     * @param input - A height map
     * @return bump map
     */
    @Override
    public ImageSource apply(ImageSource input) {
        int w = input.getWidth();
        int h = input.getHeight();

        MatrixSource output = new MatrixSource(input);

        Vector3 n = new Vector3(0, 0, 1);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                if (x < border || x == w - border || y < border || y == h - border) {
                    output.setRGB(x, y, VectorHelper.Z_NORMAL);
                    continue;
                }

                float s0 = input.getR(x - 1, y + 1);
                float s1 = input.getR(x, y + 1);
                float s2 = input.getR(x + 1, y + 1);
                float s3 = input.getR(x - 1, y);
                float s5 = input.getR(x + 1, y);
                float s6 = input.getR(x - 1, y - 1);
                float s7 = input.getR(x, y - 1);
                float s8 = input.getR(x + 1, y - 1);

                float nx = -(s2 - s0 + 2 * (s5 - s3) + s8 - s6);
                float ny = -(s6 - s0 + 2 * (s7 - s1) + s8 - s2);

                n.set(nx, ny, scale);
                n.nor();

                int rgb = VectorHelper.vectorToColor(n);
                output.setRGB(x, y, rgb);
            }
        }

        return new MatrixSource(output);
    }

}
