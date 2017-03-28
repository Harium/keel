package br.com.etyllica.keel.awt.source;

import java.awt.image.BufferedImage;

import br.com.etyllica.keel.core.source.ImageSource;


public class BufferedImageSource implements ImageSource {

	private BufferedImage image;
	
	public BufferedImageSource() {
		super();
	}
	
	public BufferedImageSource(BufferedImage image) {
		super();
		this.image = image;
	}

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public int getHeight() {
		return image.getHeight();
	}

	@Override
	public int getRGB(int x, int y) {
		return image.getRGB(x, y);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
}
