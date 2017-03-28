package br.com.etyllica.keel.classifier;

public interface Classifier<T, R> {
    R classify(T feature);
}
