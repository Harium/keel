package br.com.etyllica.motion.core;

import java.awt.image.BufferedImage;

import br.com.etyllica.motion.feature.Component;

public interface ProcessComponentFilter<T> {

	public T process(BufferedImage buffer, Component component);

}
