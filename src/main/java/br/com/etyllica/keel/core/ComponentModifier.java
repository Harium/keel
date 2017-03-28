package br.com.etyllica.keel.core;


public interface ComponentModifier<C, T> {
	
	public T modify(C component);
	
}
