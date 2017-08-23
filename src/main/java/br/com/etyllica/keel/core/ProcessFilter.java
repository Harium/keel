package br.com.etyllica.keel.core;

import java.awt.image.BufferedImage;

public interface ProcessFilter<T> {

	T process(BufferedImage buffer);
		
}
