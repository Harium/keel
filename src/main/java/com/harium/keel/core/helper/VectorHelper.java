package com.harium.keel.core.helper;

import com.badlogic.gdx.math.Vector3;

public class VectorHelper {

    public static final int Z_NORMAL = ColorHelper.getRGB(127, 127, 255);
    private static final float RGB_FACTOR = 127.5f;

    public static int vectorToColor(Vector3 cross) {
        int r = (int) ((cross.x + 1) * RGB_FACTOR);
        int g = (int) ((cross.y + 1) * RGB_FACTOR);
        int b = (int) ((cross.z + 1) * RGB_FACTOR);

        int rgb = ColorHelper.getRGB(r, g, b);
        return rgb;
    }

}
