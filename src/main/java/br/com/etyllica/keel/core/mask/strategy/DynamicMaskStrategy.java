package br.com.etyllica.keel.core.mask.strategy;


import br.com.etyllica.keel.core.source.ImageSource;

public interface DynamicMaskStrategy {
    void reset(int[][] mask);

    void update(ImageSource source, int[][] mask);
}
