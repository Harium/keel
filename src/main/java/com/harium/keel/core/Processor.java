package com.harium.keel.core;

import com.harium.keel.core.source.ImageSource;

/**
 * Processors answer questions about an image source.
 *
 * E.g.:
 * What is the average color?
 * How many white pixels?
 * If I start at the point x,y where is the nearest border with a different color?
 *
 * @param <T>
 */
public interface Processor<T> extends Modifier<ImageSource, T> {

}
