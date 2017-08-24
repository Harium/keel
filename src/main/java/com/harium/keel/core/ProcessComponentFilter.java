package com.harium.keel.core;

import java.awt.image.BufferedImage;

import com.harium.keel.feature.Component;

public interface ProcessComponentFilter<T> {

	T process(BufferedImage buffer, Component component);

}
