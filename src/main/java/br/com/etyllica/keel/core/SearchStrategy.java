package br.com.etyllica.keel.core;

import java.util.List;

import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;


public interface SearchStrategy {

	public Component filterFirst(ImageSource source, Component component);
	
	public List<Component> filter(ImageSource source, Component component);
		
	public void setup(int w, int h);
	
	public void setStep(int step);
	
}
