package com.harium.keel.core.strategy;

public interface FeatureValidationStrategy<T> {

	boolean validate(T component);
	
}
