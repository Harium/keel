package application.examples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.TrackingByColorFilter;
import br.com.etyllica.util.SVGColor;

public class FaceSkinFilterStatic extends Application {

	private FakeCamera cam = new FakeCamera();

	private TrackingByColorFilter skinFilter;

	private boolean hide = false;
	private boolean pixels = true;
	private boolean drawCleanedOnly = false;
	private boolean drawBox = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private final int IMAGES_TO_LOAD = 50;	
	
	private List<Component> skinFeatures;
	
	private Component screen;
	
	public FaceSkinFilterStatic(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Loading Images";

		for(int i=1;i<=IMAGES_TO_LOAD;i++){
			loading = i;
			
			cam.addImage("skin/skin"+Integer.toString(i)+".jpg");
		}
		
		int width = cam.getBufferedImage().getWidth();
		int height = cam.getBufferedImage().getHeight();
		
		loadingPhrase = "Configuring Filter";
		
		skinFilter = new TrackingByColorFilter(width, height, new Color(0xAF, 0x80, 0x66), 0x26);
				
		//skinFilter.addComponentStrategy(new MinComponentDimension(30));
		//skinFilter.addComponentStrategy(new MaxComponentDimension(w/2));
		
		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}

	private void reset(BufferedImage b){
		int w = b.getWidth();
		int h = b.getHeight();
		
		screen = new Component(0, 0, w, h);
		
		//Sampled
		skinFeatures = skinFilter.filter(b, screen);
				
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			cam.nextFrame();
			reset(cam.getBufferedImage());
		}

		else if(event.isKeyDown(KeyEvent.TSK_SETA_ESQUERDA)){
			cam.previousFrame();
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.TSK_P)){
			pixels = !pixels;
		}

		if(event.isKeyDown(KeyEvent.TSK_C)){
			drawCleanedOnly = !drawCleanedOnly;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_B)){
			drawBox = !drawBox;
		}


		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {
		
		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(Color.BLUE);
		
		g.setBasicStroke(2);
		for(Component skin: skinFeatures){
			
			g.setColor(SVGColor.BLUE_VIOLET);
			g.drawRect(skin.getRectangle());
			
		}

	}
	
}
