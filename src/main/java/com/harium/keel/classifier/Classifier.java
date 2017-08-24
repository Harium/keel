package com.harium.keel.classifier;

public interface Classifier<T, R> {
    R classify(T feature);
}
