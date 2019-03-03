package com.harium.keel.core.pipeline.benchmark;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

public class BenchmarkSource extends MatrixSource {

    private int operations = 0;
    private ImageSource source;

    public BenchmarkSource(ImageSource input) {
        super(input.getWidth(), input.getHeight());
        this.source = input;
    }

    public int getRGB(int x, int y) {
        operations++;
        matrix[y][x]++;
        return source.getRGB(x, y);
    }

    public void setRGB(int x, int y, int rgb) {
        operations++;
        matrix[y][x]++;
        source.setRGB(x, y, rgb);
    }

    public void setRGB(int x, int y, int rgb, int alpha) {
        operations++;
        matrix[y][x]++;
        source.setRGB(x, y, rgb, alpha);
    }

    public void reset() {
        operations = 0;
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                matrix[j][i] = 0;
            }
        }
    }

    public int getOperations() {
        return operations;
    }
}
