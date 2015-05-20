package examples.medium.application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.validation.MinComponentDimension;

public class TrackingColorApplication extends Application {

	private CameraV4L4J cam;

	private ColorFilter blueFilter;

	//Blue Marker
	private Color color = new Color(50,69,75);
	private int tolerance = 18;

	private boolean hide = false;
	private boolean pixels = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private Component screen;
	
	private List<Component> blueComponents;

	public TrackingColorApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingInfo = "Loading Images";

		cam = new CameraV4L4J(0);

		int w = cam.getBufferedImage().getWidth();
		int h = cam.getBufferedImage().getHeight();

		screen = new Component(0, 0, w, h);

		blueFilter = new ColorFilter(w, h, color, tolerance);
		blueFilter.getSearchStrategy().addValidation(new MinComponentDimension(22));
		blueFilter.getColorStrategy().setMinToleranceRed(8);
		blueFilter.getColorStrategy().setMinToleranceGreen(8);
		blueFilter.getColorStrategy().setMinToleranceBlue(8);

		final int MAGIC_NUMBER = 3;//Higher = Faster and less precise

		blueFilter.getSearchStrategy().setBorder(MAGIC_NUMBER);
		blueFilter.getSearchStrategy().setStep(2);		

		loadingInfo = "Configuring Filter";

		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}
	
	int bx = 0;
	int by = 0;
	int bRadius = 0;
	
	private void reset(BufferedImage b){
		blueComponents = blueFilter.filter(b, screen);
		
		if(!blueComponents.isEmpty()) {
			
			bx = 0;
			by = 0;
			
			bRadius = 0;
			
			for(Component component: blueComponents) {
				Point2D p = component.getCenter();
				bx += p.getX();
				by += p.getY();
				
				bRadius += (component.getW()+component.getH())/4;
			}
			
			bx /= blueComponents.size();
			by /= blueComponents.size();
			
			bRadius /= blueComponents.size();
			
			return;
		}
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			color = pickColor(event.getX(), event.getY());
			blueFilter.setColor(color);

			System.out.println(color.getRed());
			System.out.println(color.getGreen());
			System.out.println(color.getBlue());
			System.out.println("---------");
		}

		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	private Color pickColor(int px, int py) {
		return new Color(cam.getBufferedImage().getRGB(px, py));
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.TSK_P)){
			pixels = !pixels;
		}

		if(event.isKeyUp(KeyEvent.TSK_EQUALS)) {
			tolerance++;
			blueFilter.setTolerance(tolerance);
		} else if(event.isKeyUp(KeyEvent.TSK_MINUS)) {
			tolerance--;
			blueFilter.setTolerance(tolerance);
		}

		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {

		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(color);
		g.fillRect(0, 0, 40, 60);

		g.setColor(Color.BLACK);

		g.drawString("T: "+Integer.toString(tolerance), 20, 90);

		reset(cam.getBufferedImage());

		g.setAlpha(60);
		//drawFeaturedPoints(g, sampledFeature, Color.GREEN);
		g.setAlpha(100);

		g.setColor(Color.GREEN);
		
		if(blueComponents != null) {
			for(Component component:blueComponents) {
				g.drawPolygon(component.getBoundingBox());
			}
		}
		
		g.fillCircle(bx, by, bRadius);
	}
}