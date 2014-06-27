package br.com.etyllica.motion.core;

import java.awt.image.BufferedImage;

public interface ProcessFilter<T> {

	public T process(BufferedImage buffer);
		
}
