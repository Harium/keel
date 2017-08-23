package br.com.etyllica.keel.core;

import java.util.List;

import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;


public interface SearchStrategy {

	Component filterFirst(ImageSource source, Component component);
	
	List<Component> filter(ImageSource source, Component component);
		
	void setup(int w, int h);
	
	void setStep(int step);
	
}
