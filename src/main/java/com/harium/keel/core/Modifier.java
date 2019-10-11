package com.harium.keel.core;

/**
 * Modifiers transforms objects from type I to type T
 * @param <I>
 * @param <T>
 */
public interface Modifier<I, T> {

    T apply(I feature);

}
