package br.com.etyllica.motion.core;

import br.com.etyllica.motion.core.features.Component;

public interface ComponentModifier<T> {
	
	public T modify(Component component);
	
}
