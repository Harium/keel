package br.com.etyllica.keel.core;

import java.awt.image.BufferedImage;

public interface ProcessFilter<T> {

	public T process(BufferedImage buffer);
		
}
