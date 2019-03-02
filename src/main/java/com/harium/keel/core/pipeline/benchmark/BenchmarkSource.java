package com.harium.keel.core.pipeline.benchmark;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;

public class BenchmarkSource extends MatrixSource {

    private ImageSource source;

    public BenchmarkSource(ImageSource input) {
        super(input.getWidth(), input.getHeight());
        this.source = input;
    }

    public int getRGB(int x, int y) {
        matrix[y][x]++;
        return source.getRGB(x, y);
    }

    public void reset() {
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                matrix[j][i] = 0;
            }
        }
    }

}
