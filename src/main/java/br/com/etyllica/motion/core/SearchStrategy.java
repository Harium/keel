package br.com.etyllica.motion.core;

import java.util.List;

import br.com.etyllica.motion.core.source.ImageSource;
import br.com.etyllica.motion.feature.Component;


public interface SearchStrategy {

	public Component filterFirst(ImageSource source, Component component);
	
	public List<Component> filter(ImageSource source, Component component);
		
	public void setup();
	
	public void setStep(int step);
	
}
