package examples.hard.face;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.TrackingByMultipleColorFilter;
import br.com.etyllica.motion.filter.image.ContrastQuickProcessor;
import br.com.etyllica.util.SVGColor;

public class PirateHatApplication extends Application {

	private FakeCamera cam = new FakeCamera();

	private TrackingByMultipleColorFilter skinFilter;

	private boolean hide = true;
	private boolean pixels = true;
	private boolean drawCleanedOnly = false;
	private boolean drawBox = true;

	private int xOffset = 0;
	private int yOffset = 0;
	
	private final int minDensity = 22;
	private final int maxDensity = 50;
	

	private final int IMAGES_TO_LOAD = 52;	

	private List<Component> skinFeatures;

	private Component screen;

	private ImageLayer pirateHat;

	private Component overMouse = null;

	public PirateHatApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Loading Images";

		for(int i=1;i<=IMAGES_TO_LOAD;i++) {
			loading = i;

			cam.addImage("skin/skin"+Integer.toString(i)+".jpg");
		}

		loadingPhrase = "Configuring Filter";

		configureSkinFilter();
		pirateHat = new ImageLayer("effects/piratehat.png");

		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
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

		skinFilter.addColor(new Color(0xF5, 0xC4, 0xCD), tolerance, tolerance, highTolerance);

		skinFilter.addColor(new Color(0xEA, 0x90, 0x90), lowTolerance, lowTolerance, lowTolerance);
		
		skinFilter.addColor(new Color(0xE4, 0xC2, 0xBA), tolerance, lowTolerance, highTolerance);

		skinFilter.addColor(new Color(0xE1, 0x79, 0x78), lowTolerance);

		skinFilter.addColor(new Color(0xE0, 0xB0, 0xB0), tolerance, tolerance, lowTolerance);
		//skinFilter.addColor(new Color(0xE0, 0xB0, 0xA0), tolerance, tolerance, lowTolerance);
		
		skinFilter.addColor(new Color(0xd0, 0x9b, 0x8b), lowTolerance);
		//Secundary Hue Scale
		skinFilter.addColor(new Color(0xd0, 0x7e, 0x5f), lowTolerance);

		skinFilter.addColor(new Color(0xC6, 0x8D, 0x82), lowTolerance);

		skinFilter.addColor(new Color(0xB6, 0x7C, 0x70), tolerance);

		skinFilter.addColor(new Color(0xA3, 0x6A, 0x5F), tolerance);
		
		//skinFilter.addColor(new Color(0x90, 0x5C, 0x4F), tolerance);

		//skinFilter.addColor(new Color(0xa4, 0x91, 0x95), tolerance), 
		
		skinFilter.addColor(new Color(0xA0, 0x88, 0x88), tolerance, lowTolerance, lowTolerance);
		
		
		skinFilter.addColor(new Color(0x7B, 0x4B, 0x41), tolerance);

		skinFilter.addColor(new Color(0x65, 0x3D, 0x35), tolerance, highTolerance, highTolerance);

		skinFilter.addColor(new Color(0x4E, 0x2F, 0x2A), tolerance, lowTolerance, lowTolerance);

		//Shadow and Mouth
		skinFilter.addColor(new Color(0x6B, 0x57, 0x60), tolerance);

		//skinFilter.addComponentStrategy(new MinDensityValidation(minDensity));
		//skinFilter.addComponentStrategy(new MaxDensityValidation(maxDensity));
		//skinFilter.addComponentStrategy(new MinComponentDimension(40));//Avoid small noises
		//skinFilter.addComponentStrategy(new CountComponentPoints(180));//Avoid small noises
		//skinFilter.addComponentStrategy(new MaxComponentDimension(w/2));

	}

	Component biggestComponent = null;

	private void reset(BufferedImage b) {
		int w = b.getWidth();
		int h = b.getHeight();

		screen = new Component(0, 0, w, h);

		//Sampled
		skinFeatures = skinFilter.filter(b, screen);


		//TODO Merge components

		biggestComponent = findBiggestComponent(skinFeatures);

		//biggestComponent = mergeComponents(biggestComponent, skinFeatures);		

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		int mx = event.getX();

		int my = event.getY();

		if(!skinFeatures.isEmpty()) {

			for(Component component: skinFeatures) {

				if(component.colidePoint(mx, my)) {

					overMouse = component;

				}
			}

		}

		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {

			BufferedImage buffer = cam.getBufferedImage();

			if(mx<buffer.getWidth()&&my<buffer.getHeight()) {

				final int toleranceByClick = 0x10;  

				int rgb = buffer.getRGB(mx, my);

				Color color = new Color(rgb);

				skinFilter.addColor(color, toleranceByClick);

				String redString = Integer.toString(color.getRed(), 16).toUpperCase();
				String greenString = Integer.toString(color.getGreen(), 16).toUpperCase();
				String blueString = Integer.toString(color.getBlue(), 16).toUpperCase();
								
				System.out.println("skinFilter.addColor(new Color(0x"+redString+", 0x"+greenString+", 0x"+blueString+"), 0x10);");

				reset(buffer);
			}

		} else if (event.onButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)) {

			configureSkinFilter();

			BufferedImage buffer = cam.getBufferedImage();

			reset(buffer);
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

		g.setColor(Color.BLACK);

		if(!hide){

			BufferedImage image = new ContrastQuickProcessor().process(cam.getBufferedImage());

			g.drawImage(image, xOffset, yOffset);
			//g.fillRect(0, 0, 640, 480);

		}

		g.setColor(Color.BLUE);
		g.setAlpha(80);

		g.setBasicStroke(2);

		drawFeatures(skinFeatures, g);

		drawFeatureInfo(g);

	}

	private void drawFeatureInfo(Graphic g) {

		g.setColor(Color.BLACK);

		g.setFontSize(20);

		if(overMouse!=null) {

			int offsetX = 650;

			g.drawShadow(offsetX, 40, "x: "+overMouse.getX());

			g.drawShadow(offsetX, 60, "y: "+overMouse.getY());

			g.drawShadow(offsetX, 80, "w: "+overMouse.getW());

			g.drawShadow(offsetX, 100, "h: "+overMouse.getH());

			g.drawShadow(offsetX, 120, "area: "+overMouse.getArea());

			g.drawShadow(offsetX, 140, "dens: "+(int)overMouse.getDensity()+"%");

		}

	}

	private void drawFeatures(List<Component> features, Graphic g) {

		//List<Component> features = mergeComponents(skinFeatures);

		for(Component component: features) {

			if(component != overMouse) {

				if(component.getDensity()<minDensity) {
					
					//g.setColor(SVGColor.HONEYDEW);
					g.setColor(SVGColor.LEMON_CHIFFON);
					
				} else if(component.getDensity()>maxDensity) {
					
					g.setColor(SVGColor.DARK_GOLDENROD);
					//g.setColor(SVGColor.LEMON_CHIFFON);
					
				} else {
					g.setColor(SVGColor.BLUE_VIOLET);	
				}

			} else {

				g.setColor(SVGColor.RED);

			}

			g.drawRect(component.getRectangle());

			g.setBasicStroke(1);

			for(Point2D point: component.getPoints()) {

				g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);

			}

		}

		drawPirateHat(features, g);

	}

	private void drawPirateHat(List<Component> components, Graphic g) {

		if(!components.isEmpty()) {

			double angle = drawAndCalculateAngle(biggestComponent, g);

			double hatScale = 2;//The Pirate Hat has the double of the head width

			double scale = ((double)biggestComponent.getW()*hatScale/(double)this.w);

			pirateHat.setScale(scale);
			pirateHat.setAngle(angle-90);

			pirateHat.centralizeX(biggestComponent.getX(), biggestComponent.getX()+biggestComponent.getW());

			pirateHat.setY(biggestComponent.getY()-(int)((pirateHat.getH())*scale));

			pirateHat.draw(g);

		}

	}

	private Component mergeComponents(Component biggestComponent, List<Component> components) {

		components.remove(biggestComponent);

		for(int i = components.size()-1; i > 0; i--) {

			Component candidate = components.get(i);

			if(biggestComponent.colide(candidate)) {

				biggestComponent.merge(candidate);

				components.remove(i);

			}

		}

		components.add(0, biggestComponent);

		return biggestComponent;

	}

	private Component findBiggestComponent(List<Component> components) {

		if(components.isEmpty()) {
			return null;
		}

		Component biggestComponent = components.get(0);

		double biggestArea = 0;

		for(int i=0;i<components.size(); i++) {

			Component candidate = components.get(i);

			//double weight = candidate.getArea()*candidate.getDensity();
			double weight = candidate.getArea()*candidate.getH();

			if(weight > biggestArea) {
				biggestComponent = candidate;
				biggestArea = weight;
			}

		}

		return biggestComponent;

	}

	private double drawAndCalculateAngle(Component component, Graphic g) {

		int upperPoints = 0, upperX = 0, upperY = 0;

		int lowerPoints = 0, lowerX = 0, lowerY = 0;

		int centerY = component.getY()+component.getH()/2;

		for(Point2D point: component.getPoints()) {

			//Point lower
			if(point.getY()>centerY) {
				lowerPoints++;
				lowerX += point.getX();
				lowerY += point.getY();
			} else {
				upperPoints++;
				upperX += point.getX();
				upperY += point.getY();
			}

		}

		g.setColor(Color.BLACK);
		g.drawLine(component.getX(), centerY, component.getX()+component.getW(), centerY);

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
