package br.com.etyllica.motion.core.strategy;


public interface PixelStrategy {
	public boolean validateColor(int rgb);
	public boolean strongValidateColor(int rgb, int strength);
}
