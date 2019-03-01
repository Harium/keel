package com.harium.keel.core;


public interface Modifier<I, T> {

    T apply(I feature);

}
