package com.harium.keel.core.source;

public class MatrixSource extends ImageSourceImpl {

    private int w = 0;
    private int h = 0;

    private int[][] matrix;

    public MatrixSource(int w, int h) {
        super();
        this.w = w;
        this.h = h;

        matrix = new int[h][w];
    }

    public MatrixSource(int[][] matrix) {
        super();
        this.w = matrix[0].length;
        this.h = matrix.length;
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getRGB(int x, int y) {
        return matrix[y][x];
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

}
