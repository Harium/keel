package br.com.etyllica.motion.camera;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.loader.image.ImageLoader;

public class FakeCamera implements Camera{

	private List<BufferedImage> list = new ArrayList<BufferedImage>();
	
	private int currentFrame = 0;
	
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
		return list.get(currentFrame);
	}
	
	public void nextFrame(){
		currentFrame++;
		currentFrame%=list.size();
	}
	
	public void previousFrame(){
		currentFrame+=list.size()-1;
		currentFrame%=list.size();
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
}
