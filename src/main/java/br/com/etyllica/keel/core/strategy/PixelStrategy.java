package br.com.etyllica.keel.core.strategy;


public interface PixelStrategy {
	boolean validateColor(int rgb, int j, int i);
	boolean strongValidateColor(int rgb, int j, int i, int strength);
}
