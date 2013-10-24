package br.com.etyllica.camera;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.core.loader.ImageLoader;

public class FakeCamera implements Camera{

	private List<BufferedImage> list = new ArrayList<BufferedImage>();
	
	private int currentImage = 0;
	
	public FakeCamera(){
		super();
	}
	
	public FakeCamera(String path){
		super();
		
		addImage(path);
	}
	
	public void addImage(String path){
		list.add(ImageLoader.getInstance().getImage(path));
	}

	@Override
	public BufferedImage getBufferedImage() {
		return list.get(currentImage);
	}
	
	public void nextFrame(){
		currentImage++;
		currentImage%=list.size();
	}
	
	public void previousFrame(){
		currentImage+=list.size()-1;
		currentImage%=list.size();
	}

}
