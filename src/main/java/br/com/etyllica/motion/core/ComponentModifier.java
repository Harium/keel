package br.com.etyllica.motion.core;

import br.com.etyllica.motion.feature.Component;

public interface ComponentModifier<T> {
	
	public T modify(Component component);
	
}
