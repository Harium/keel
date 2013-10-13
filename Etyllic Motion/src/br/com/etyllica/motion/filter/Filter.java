package br.com.etyllica.motion.filter;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.motion.features.Componente;


public interface Filter {

	public List<Componente> filter(BufferedImage bimg, List<Componente> components);
	
	public boolean validateColor(int rgb);

	public boolean validateComponent(Componente component);
	
}
