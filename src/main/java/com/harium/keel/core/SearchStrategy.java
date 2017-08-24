package com.harium.keel.core;

import java.util.List;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.feature.Component;


public interface SearchStrategy {

	Component filterFirst(ImageSource source, Component component);
	
	List<Component> filter(ImageSource source, Component component);
		
	void setup(int w, int h);
	
	void setStep(int step);
	
}
