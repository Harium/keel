package br.com.etyllica.keel.filter.dummy;

import br.com.etyllica.keel.core.strategy.MaskStrategy;

public class DummyMaskStrategy implements MaskStrategy {

    @Override
    public boolean validateMask(int x, int y) {
        return true;
    }
}
