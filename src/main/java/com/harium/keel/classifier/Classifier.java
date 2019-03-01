package com.harium.keel.classifier;

import com.harium.keel.core.Modifier;

public abstract class Classifier<I, O> implements Modifier<I, O> {

    abstract O classify(I feature);

    @Override
    public O apply(I feature) {
        return classify(feature);
    }
}
