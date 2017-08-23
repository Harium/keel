package br.com.etyllica.keel.core;

import java.awt.image.BufferedImage;

import br.com.etyllica.keel.feature.Component;

public interface ProcessComponentFilter<T> {

	T process(BufferedImage buffer, Component component);

}
