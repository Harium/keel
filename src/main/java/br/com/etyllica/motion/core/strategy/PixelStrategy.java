package br.com.etyllica.motion.core.strategy;


public interface PixelStrategy {
	public boolean validateColor(int rgb, int j, int i);
	public boolean strongValidateColor(int rgb, int j, int i, int strength);
}
