package br.com.etyllica.camera;

import br.com.etyllica.layer.ImageLayer;

public class FakeCamera {

	ImageLayer layer;
	
	public FakeCamera(String path){
		layer = new ImageLayer(path);
	}
	
}
