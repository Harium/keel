package br.com.etyllica.motion.core;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.features.Component;


public interface Filter {

	public List<Component> filter(BufferedImage bimg, List<Component> components);
	
	public boolean validateColor(int rgb);

	public boolean validateComponent(Component component);
	
}
