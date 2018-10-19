package com.harium.keel.effect.binary;

import com.harium.keel.core.source.OneBandSource;
import org.junit.Assert;
import org.junit.Test;

public class ZhangSuenThinningTest {

    @Test
    public void testApply() {
        /*int[] array = {1, 1, 0, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 0,
                0, 0, 0, 0, 0, 0, 0};*/
        /*int[] array = {1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1,
                1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0,
                1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0};*/
        //printArray(array, 14, 7);
        /*System.out.println(" ");

        OneBandSource source = new OneBandSource(7, 14, array);

        ZhangSuenThinning effect = new ZhangSuenThinning();
        effect.apply(source);

        int[] expected = {1, 1, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 0, 0, 1,
                1, 0, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0,
                1, 0, 0, 0, 1, 0, 0,
                1, 0, 0, 0, 1, 0, 0,
                1, 0, 0, 0, 1, 0, 0,
                1, 0, 0, 0, 1, 0, 0,
                1, 0, 0, 0, 1, 0, 0,
                1, 0, 0, 0, 1, 0, 0,
                1, 0, 0, 0, 1, 0, 0,
                1, 1, 1, 1, 1, 0, 0,
                0, 0, 0, 0, 0, 0, 0};*/

        //printArray(source.getArray(), 7, 14);
        //Assert.assertArrayEquals(expected, source.getArray());
    }

    public void printArray(int[] array, int width, int height) {
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {
                System.out.print(array[i + j * width] + " ");
            }
            System.out.println(" ");
        }
    }

    public static int[][] transposeMatrix(int[][] m) {
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

}
