package application.examples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.TrackingByMultipleColorFilter;
import br.com.etyllica.motion.filter.validation.MaxComponentDimension;
import br.com.etyllica.motion.filter.validation.MinComponentDimension;
import br.com.etyllica.util.SVGColor;

public class FaceSkinFilterStatic extends Application {

	private FakeCamera cam = new FakeCamera();

	private TrackingByMultipleColorFilter skinFilter;

	private boolean hide = false;
	private boolean pixels = true;
	private boolean drawCleanedOnly = false;
	private boolean drawBox = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private final int IMAGES_TO_LOAD = 50;	

	private List<Component> skinFeatures;

	private Component screen;
	
	private ImageLayer pirateHat;

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

		configureSkinFilter(width, height);
		pirateHat = new ImageLayer("effects/piratehat.png");
		
		loading = 60;
		reset(cam.getBufferedImage());
		
		loading = 100;
	}
	
	private void configureSkinFilter(int width, int height) {
		
		skinFilter = new TrackingByMultipleColorFilter(width, height);
		
		//skinFilter.addColor(new Color(0xAF, 0x80, 0x66), 0x26);

		//F5 EA E0 D4 C6 B6 A3 90 7B 65 4E
		//C4 C2 B0 A0 8D 7C 6A 5C 4B 3D 2F
		//CD BA A6 93 82 70 5F 4F 41 35 2A
		
		int tolerance = 0x16;

		skinFilter.addColor(new Color(0xF5, 0xC4, 0xCD), tolerance);

		skinFilter.addColor(new Color(0xE4, 0xC2, 0xBA), tolerance);

		skinFilter.addColor(new Color(0xE0, 0xB0, 0xA6), tolerance);

		skinFilter.addColor(new Color(0xD4, 0xA0, 0x93), tolerance);

		skinFilter.addColor(new Color(0xC6, 0x8D, 0x82), tolerance);

		skinFilter.addColor(new Color(0xB6, 0x7C, 0x70), tolerance);

		skinFilter.addColor(new Color(0xA3, 0x6A, 0x5F), tolerance);

		skinFilter.addColor(new Color(0x90, 0x5C, 0x4F), tolerance);

		skinFilter.addColor(new Color(0x7B, 0x4B, 0x41), tolerance);

		skinFilter.addColor(new Color(0x65, 0x3D, 0x35), tolerance);

		skinFilter.addColor(new Color(0x4E, 0x2F, 0x2A), tolerance);

		//skinFilter.addComponentStrategy(new DensityValidation(30));
		skinFilter.addComponentStrategy(new MinComponentDimension(30));//Avoid small noises
		skinFilter.addComponentStrategy(new MaxComponentDimension(w/2));
		
	}

	private void reset(BufferedImage b) {
		int w = b.getWidth();
		int h = b.getHeight();

		screen = new Component(0, 0, w, h);

		//Sampled
		skinFeatures = skinFilter.filter(b, screen);

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {

			int x = event.getX();

			int y = event.getY();


			BufferedImage buffer = cam.getBufferedImage();

			if(x<buffer.getWidth()&&y<buffer.getHeight()) {

				int rgb = buffer.getRGB(x, y);

				skinFilter.addColor(new Color(rgb), 0x10);

				reset(buffer);	
			}

		}

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
		g.setAlpha(80);

		g.setBasicStroke(2);

		for(Component skin: skinFeatures) {

			g.setColor(SVGColor.BLUE_VIOLET);
			g.drawRect(skin.getRectangle());

			g.setBasicStroke(1);
			
			for(Point2D point: skin.getPoints()) {

				g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);

			}

			drawAngle(skin, g);

		}
		
		drawPirateHat(skinFeatures, g);
		
	}
	
	private void drawPirateHat(List<Component> components, Graphic g) {
		
		if(!skinFeatures.isEmpty()) {
			
			Component biggestComponent = findBiggestComponent(skinFeatures);
		
			double scale = ((double)biggestComponent.getW()*1.4/(double)this.w);
						
			pirateHat.setScale(scale);
			
			pirateHat.centralizeX(biggestComponent.getX(), biggestComponent.getX()+biggestComponent.getW());
			pirateHat.setY(biggestComponent.getY()-(int)((pirateHat.getH())*scale));
			
			pirateHat.draw(g);
			
		}
		
	}
	
	private Component findBiggestComponent(List<Component> components) {

		Component biggestComponent = components.get(0);

		int biggestArea = 0;

		for(int i=0;i<components.size(); i++) {

			Component candidate = components.get(i);

			if(candidate.getArea() > biggestArea) {
				biggestComponent = candidate;
				biggestArea = candidate.getArea();
			}

		}

		return biggestComponent;

	}

	private void drawAngle(Component component, Graphic g) {

		int upperPoints = 0, upperX = 0, upperY = 0;

		int lowerPoints = 0, lowerX = 0, lowerY = 0;

		for(Point2D point: component.getPoints()) {

			//Point lower
			if(point.getY()>component.getX()+component.getH()/2) {
				lowerPoints++;
				lowerX += point.getX();
				lowerY += point.getY();
			} else {
				upperPoints++;
				upperX += point.getX();
				upperY += point.getY();
			}

		}

		if(upperPoints>0&&lowerPoints>0) {

			Point2D lowerPoint = new Point2D(lowerX/lowerPoints, lowerY/lowerPoints);

			Point2D upperPoint = new Point2D(upperX/upperPoints, upperY/upperPoints);

			g.setColor(Color.BLACK);
			g.fillCircle(lowerPoint, 5);
			g.fillCircle(upperPoint, 5);

			g.setColor(Color.BLUE);
			g.drawCircle(lowerPoint, 5);
			g.drawCircle(upperPoint, 5);

			g.drawLine(lowerPoint, upperPoint);

		}

	}

}
