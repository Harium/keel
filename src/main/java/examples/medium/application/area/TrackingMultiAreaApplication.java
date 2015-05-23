package examples.medium.application.area;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.event.PointerState;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.validation.MinDensityValidation;
import br.com.etyllica.motion.filter.validation.MinDimensionValidation;

public class TrackingMultiAreaApplication extends Application {

	private FakeCamera cam;	

	//Orange Marker
	private ColorFilter orangeFilter;
	private Color orangeColor = new Color(165, 94, 74);
	
	//Blue Marker
	private ColorFilter blueFilter;
	private Color blueColor = new Color(54, 71, 79);

	private MultiArea area = new MultiArea(5);

	private int tolerance = 10;
	private int minDensity = 12;
	private int minDimension = 10;

	private MinDensityValidation densityValidation;
	private MinDimensionValidation dimensionValidation;

	private boolean hide = false;
	private boolean markers = true;
	private boolean pixels = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private Component screen;
	
	//Area Stuff
	private int areaOver = -1;

	private List<Component> orangeComponents;
	private List<Component> blueComponents;

	public TrackingMultiAreaApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingInfo = "Loading Images";

		screen = setupCamera();

		densityValidation = new MinDensityValidation(minDensity);
		dimensionValidation = new MinDimensionValidation(minDimension);

		orangeFilter = new ColorFilter(screen.getW(), screen.getH(), orangeColor, tolerance);
		orangeFilter.getSearchStrategy().addValidation(dimensionValidation);
		orangeFilter.getSearchStrategy().addValidation(densityValidation);
		
		blueFilter = new ColorFilter(screen.getW(), screen.getH(), blueColor, tolerance);
		blueFilter.getSearchStrategy().addValidation(dimensionValidation);

		final int MAGIC_NUMBER = 3;//Higher = Faster and less precise

		orangeFilter.getSearchStrategy().setBorder(MAGIC_NUMBER);
		orangeFilter.getSearchStrategy().setStep(2);
		blueFilter.getSearchStrategy().setBorder(MAGIC_NUMBER);
		blueFilter.getSearchStrategy().setStep(2);

		loadingInfo = "Configuring Filter";

		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}

	protected Component setupCamera() {
		cam = new FakeCamera();

		cam.addImage("dumbbells/dumbbells3.png");
		
		int w = cam.getBufferedImage().getWidth();
		int h = cam.getBufferedImage().getHeight();

		screen = new Component(0, 0, w, h);

		return screen;
	}

	int bx = 0;
	int by = 0;
	int bRadius = 0;

	private void reset(BufferedImage b){
		orangeComponents = orangeFilter.filter(b, screen);
		blueComponents = blueFilter.filter(b, screen);
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			orangeColor = pickColor(event.getX(), event.getY());
			orangeFilter.setColor(orangeColor);

			System.out.println(orangeColor.getRed());
			System.out.println(orangeColor.getGreen());
			System.out.println(orangeColor.getBlue());
			System.out.println("---------");
		}

		if(event.getState() == PointerState.MOVE) {
			if(area.getPolygons()!=null) {
				int i=1;
				areaOver = -1;
				for(Polygon polygon: area.getPolygons()) {
					if(polygon.contains(event.getX(), event.getY())) {
						//System.out.println("Mouse over "+i);
						areaOver = i;
					}
					i++;
				}
			}
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

		if(event.isKeyDown(KeyEvent.TSK_J)){
			markers = !markers;
		}

		//Change Tolerance
		if(event.isKeyUp(KeyEvent.TSK_EQUALS)) {
			tolerance++;
			orangeFilter.setTolerance(tolerance);
		} else if(event.isKeyUp(KeyEvent.TSK_MINUS)) {
			tolerance--;
			orangeFilter.setTolerance(tolerance);
		}

		//Change Density
		if(event.isKeyUp(KeyEvent.TSK_P)) {
			minDensity++;
			densityValidation.setDensity(minDensity);
		} else if(event.isKeyUp(KeyEvent.TSK_O)) {
			minDensity--;
			densityValidation.setDensity(minDensity);
		}

		//Change Dimension
		if(event.isKeyUp(KeyEvent.TSK_L)) {
			minDimension++;
			dimensionValidation.setDimension(minDimension);
		} else if(event.isKeyUp(KeyEvent.TSK_K)) {
			minDimension--;
			dimensionValidation.setDimension(minDimension);
		}

		if(event.isKeyUp(KeyEvent.TSK_RIGHT_ARROW)) {
			cam.nextFrame();
		} else if(event.isKeyUp(KeyEvent.TSK_LEFT_ARROW)) {
			cam.previousFrame();
		}

		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {

		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(orangeColor);
		g.fillRect(0, 0, 60, 80);

		g.setColor(Color.BLACK);

		reset(cam.getBufferedImage());

		if(markers) {

			g.drawString("Tol: "+Integer.toString(tolerance), 10, 80);
			g.drawString("Den: "+Integer.toString(minDensity), 10, 100);
			g.drawString("Dim: "+Integer.toString(minDimension), 10, 120);

			g.setAlpha(60);
			//drawFeaturedPoints(g, sampledFeature, Color.GREEN);
			g.setAlpha(100);

			g.setColor(Color.GREEN);

			Component c1 = null, c2 = null;
			if(orangeComponents != null) {
				for(Component component:orangeComponents) {
					g.drawPolygon(component.getBoundingBox());
					g.drawString(component.getX(), component.getY(), component.getW(), component.getH(), Double.toString(component.getDensity()));

					if(c1==null) {
						c1 = component;
					} else if(c2 == null) {
						c2 = component;
					}
				}
			}

			if(c1!=null&&c2!=null) {

				Point2D p = c1.getCenter();
				Point2D q = c2.getCenter();

				area.generateArea(p, q);
				//Draw Areas
				g.setAlpha(50);
				g.setColor(Color.BLUE);
				
				int i = 1;
				for(Polygon polygon: area.getPolygons()) {
					if(areaOver != i) {
						g.drawPolygon(polygon);
					} else {
						g.fillPolygon(polygon);
					}
					i++;
				}
				
				g.resetOpacity();
			}

			

		}
	}
}