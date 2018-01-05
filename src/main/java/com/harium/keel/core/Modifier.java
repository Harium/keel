package com.harium.keel.core;


public interface Modifier<C, T> {
	
	T modify(C component);
	
}
