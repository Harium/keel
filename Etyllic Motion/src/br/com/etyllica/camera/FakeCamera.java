package br.com.etyllica.camera;

import java.awt.image.BufferedImage;

import br.com.etyllica.core.loader.ImageLoader;

public class FakeCamera implements Camera{

	private BufferedImage buffer;
	
	public FakeCamera(String path){
		super();
		
		this.buffer = ImageLoader.getInstance().getImage(path);
	}

	@Override
	public BufferedImage getBufferedImage() {
		// TODO Auto-generated method stub
		return buffer;
	}
		
}
