package br.com.etyllica.motion.core.strategy;


public interface PixelStrategy {
	public boolean validateColor(int rgb);
	public boolean weakValidateColor(int rgb, int reference);
}
