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

    public MatrixSource(ImageSource input) {
        this(input.getWidth(), input.getHeight());
        copy(input);
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

    @Override
    public void setRGB(int x, int y, int rgb) {
        matrix[y][x] = rgb;
    }

    public void copy(ImageSource input) {
        for (int j = 0; j < input.getHeight(); j++) {
            for (int i = 0; i < input.getWidth(); i++) {
                setRGB(i, j, input.getRGB(i, j));
            }
        }
    }
}
