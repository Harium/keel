package com.harium.keel.core.effect.bump;

import com.badlogic.gdx.math.Vector3;
import com.harium.keel.core.effect.Effect;
import com.harium.keel.core.helper.VectorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Reference: http://www.alejandrosegovia.net/2014/03/31/bump-map-generation/
 */
public class SimpleBumpMapEffect extends BumpMapEffect {

    /**
     * Simple method to generate bump map from a height map
     *
     * @param input - A height map
     * @return bump map
     */
    @Override
    public ImageSource apply(ImageSource input) {
        int w = input.getWidth();
        int h = input.getHeight();

        int[][] output = new int[h][w];

        Vector3 s = new Vector3(1, 0, 0);
        Vector3 t = new Vector3(0, 1, 0);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                if (x < border || x == w - border || y < border || y == h - border) {
                    output[y][x] = VectorHelper.Z_NORMAL;
                    continue;
                }

                float dh = input.getR(x + 1, y) - input.getR(x - 1, y);
                float dv = input.getR(x, y + 1) - input.getR(x, y - 1);

                s.set(scale, 0, dh);
                t.set(0, scale, dv);

                Vector3 cross = s.crs(t).nor();
                int rgb = VectorHelper.vectorToColor(cross);
                output[y][x] = rgb;
            }
        }

        return new MatrixSource(output);
    }

}
