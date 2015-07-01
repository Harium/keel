package br.com.etyllica.motion.core;


public interface ComponentModifier<C, T> {
	
	public T modify(C component);
	
}
