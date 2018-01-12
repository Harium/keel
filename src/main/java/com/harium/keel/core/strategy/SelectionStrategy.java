package com.harium.keel.core.strategy;


public interface SelectionStrategy {
	boolean validateColor(int rgb, int j, int i);
	boolean softValidateColor(int rgb, int j, int i, int strength);
}
