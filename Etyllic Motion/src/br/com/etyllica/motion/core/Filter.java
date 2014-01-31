package br.com.etyllica.motion.core;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.features.Component;


public interface Filter {

	public Component filterFirst(BufferedImage bimg, Component component);
	
	public List<Component> filter(BufferedImage bimg, Component component);
		
	public void setup();
	
}
