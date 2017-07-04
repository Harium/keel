package examples.misc;

import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.commons.context.Application;
import br.com.etyllica.commons.context.UpdateIntervalListener;
import br.com.etyllica.commons.event.KeyEvent;
import br.com.etyllica.commons.event.MouseEvent;
import br.com.etyllica.commons.event.PointerEvent;
import br.com.etyllica.commons.graphics.Color;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.keel.awt.camera.Camera;
import br.com.etyllica.keel.awt.camera.CameraV4L4J;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.TrackingByMultipleColorFilter;
import br.com.etyllica.keel.filter.validation.MinCountPoints;
import br.com.etyllica.keel.filter.validation.MinDensityValidation;
import br.com.etyllica.keel.filter.validation.MinDimensionValidation;
import br.com.etyllica.layer.ImageLayer;

public class FaceSkinFilter extends Application implements UpdateIntervalListener {

	private Camera cam;
	private BufferedImageSource source = new BufferedImageSource();

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

	public FaceSkinFilter(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		cam = new CameraV4L4J();
		
		loadingInfo = "Loading Images";

		loadingInfo = "Configuring Filter";

		configureSkinFilter();
		pirateHat = new ImageLayer("effects/piratehat.png");

		updateAtFixedRate(100, this);
		
		loading = 100;
	}
	
	@Override
	public void timeUpdate(long now) {
		reset(cam.getBufferedImage());
	}
	
	private void configureSkinFilter() {
		
		int width = cam.getBufferedImage().getWidth();
		int height = cam.getBufferedImage().getHeight();
		
		skinFilter = new TrackingByMultipleColorFilter(width, height);
		
		//skinFilter.addColor(new Color(0xAF, 0x80, 0x66), 0x26);

		//F5 EA E0 D4 C6 B6 A3 90 7B 65 4E
		//C4 C2 B0 A0 8D 7C 6A 5C 4B 3D 2F
		//CD BA A6 93 82 70 5F 4F 41 35 2A
		
		int tolerance = 0x14;
		
		int highTolerance = 0x19;
		
		int lowTolerance = 0x0A;				

		/*skinFilter.addColor(new Color(0xF5, 0xC4, 0xCD), tolerance, tolerance, highTolerance);

		skinFilter.addColor(new Color(0xE4, 0xC2, 0xBA), tolerance, tolerance, highTolerance);

		skinFilter.addColor(new Color(0xE0, 0xB0, 0xA6), tolerance);

		//skinFilter.addColor(new Color(0xD4, 0xA0, 0x93), tolerance/2);

		skinFilter.addColor(new Color(0xC6, 0x8D, 0x82), tolerance);
				
		skinFilter.addColor(new Color(0xB6, 0x7C, 0x70), tolerance);

		skinFilter.addColor(new Color(0xA3, 0x6A, 0x5F), tolerance);

		//skinFilter.addColor(new Color(0x90, 0x5C, 0x4F), tolerance);

		skinFilter.addColor(new Color(0x7B, 0x4B, 0x41), tolerance);
		
		skinFilter.addColor(new Color(0x65, 0x3D, 0x35), tolerance, highTolerance, highTolerance);

		skinFilter.addColor(new Color(0x4E, 0x2F, 0x2A), tolerance, lowTolerance, lowTolerance);

		//Shadow and Mouth
		skinFilter.addColor(new Color(0x6b, 0x57, 0x60), tolerance);*/

		//Very White people
		//skinFilter.addColor(new Color(0xA7, 0x85, 0x93), 0x10);
		//skinFilter.addColor(new Color(0x5d, 0x4b, 0x5b), 0x10);
		
		//Medium Color
		//skinFilter.addColor(new Color(0xC1, 0xAE, 0xb0), lowTolerance);
		
		skinFilter.addValidation(new MinDensityValidation(22));
		skinFilter.addValidation(new MinDimensionValidation(40));//Avoid small noises
		skinFilter.addValidation(new MinCountPoints(220));//Avoid small noises
		
	}

	private void reset(BufferedImage b) {
		int w = b.getWidth();
		int h = b.getHeight();

		screen = new Component(0, 0, w, h);

		source.setImage(b);
		//Sampled
		skinFeatures = skinFilter.filter(source, screen);
	}

	@Override
	public void updateMouse(PointerEvent event) {

		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)) {

			int x = event.getX();

			int y = event.getY();


			BufferedImage buffer = cam.getBufferedImage();

			if(x<buffer.getWidth()&&y<buffer.getHeight()) {

				int rgb = buffer.getRGB(x, y);

				skinFilter.addColor(new Color(rgb), 0x10);

				reset(buffer);	
			}

		}
	}

	@Override
	public void updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.VK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.VK_P)){
			pixels = !pixels;
		}

		if(event.isKeyDown(KeyEvent.VK_C)){
			drawCleanedOnly = !drawCleanedOnly;
		}

		if(event.isKeyDown(KeyEvent.VK_B)){
			drawBox = !drawBox;
		}
	}

	@Override
	public void draw(Graphics g) {

		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(Color.BLUE);
		g.setAlpha(80);

		g.setLineWidth(2);

		for(Component skin: skinFeatures) {

			g.setColor(Color.BLUE_VIOLET);
			g.drawRect(skin.getRectangle());

			g.setLineWidth(1);
			
			for(Point2D point: skin.getPoints()) {

				g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);

			}

		}
		
		drawPirateHat(skinFeatures, g);
		
	}
	
	private void drawPirateHat(List<Component> components, Graphics g) {
		
		if(!skinFeatures.isEmpty()) {
			
			Component biggestComponent = findBiggestComponent(skinFeatures);
			
			double angle = drawAndCalculateAngle(biggestComponent, g);
		
			double hatScale = 2;//The Pirate Hat has the double of the head width
			
			double scale = ((double)biggestComponent.getW()*hatScale/(double)this.w);
						
			pirateHat.setScale(scale);
			//pirateHat.setAngle(angle);
			
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

	private double drawAndCalculateAngle(Component component, Graphics g) {

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
			
			return upperPoint.angle(lowerPoint);

		}
		
		return 0d;

	}

}
