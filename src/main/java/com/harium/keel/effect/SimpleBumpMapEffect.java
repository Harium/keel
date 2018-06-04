package com.harium.keel.effect;

import com.badlogic.gdx.math.Vector3;
import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.helper.ColorHelper;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

/**
 * Reference: http://www.alejandrosegovia.net/2014/03/31/bump-map-generation/
 */
public class SimpleBumpMapEffect implements Effect {

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

        int border = 1;

        Vector3 s = new Vector3(1, 0, 0);
        Vector3 t = new Vector3(0, 1, 0);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                if (x < border || x == w - border || y < border || y == h - border) {
                    output[y][x] = Color.BLUE.getRGB();
                }

                float dh = input.getR(x + 1, y) - input.getR(x - 1, y);
                float dv = input.getR(x, y + 1) - input.getR(x, y - 1);

                s.set(1, 0, dh);
                t.set(0, 1, dv);

                Vector3 cross = s.crs(t);
                int rgb = vectorToColor(cross);
                output[y][x] = rgb;
            }
        }

        return new MatrixSource(output);
    }

    static int vectorToColor(Vector3 cross) {
        int r = (int) ((cross.x + 1) * 255) / 2;
        int g = (int) ((cross.y + 1) * 255) / 2;
        int b = (int) ((cross.z + 1) * 255) / 2;

        int rgb = ColorHelper.getRGB(r, g, b);
        return rgb;
    }
}
