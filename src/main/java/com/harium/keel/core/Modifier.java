package com.harium.keel.core;


public interface Modifier<I, T> {
	
	T modify(I feature);
	
}
