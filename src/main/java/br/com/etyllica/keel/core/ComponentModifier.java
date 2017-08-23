package br.com.etyllica.keel.core;


public interface ComponentModifier<C, T> {
	
	T modify(C component);
	
}
