package com.harium.keel.helper;

public class TypeHelper {

    public static float[] toFloatArray(double[] doubleArray) {
        float[] floatArray = new float[doubleArray.length];
        for (int i = 0; i < doubleArray.length; i++) {
            floatArray[i] = (float) doubleArray[i];
        }
        return floatArray;
    }

    public static float[] toFloatArray(double[][] doubleArray) {
        int w = doubleArray[0].length;
        int h = doubleArray.length;

        float[] floatArray = new float[w * h];
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                floatArray[j * w + i] = (float) doubleArray[j][i];
            }
        }
        return floatArray;
    }

}
