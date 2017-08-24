package com.harium.keel.core;

import java.awt.image.BufferedImage;

public interface ProcessFilter<T> {

	T process(BufferedImage buffer);
		
}
