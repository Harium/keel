package com.harium.keel.core;


public interface ComponentModifier<C, T> {
	
	T modify(C component);
	
}
