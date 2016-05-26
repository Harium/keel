package examples.medium.application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.MouseButton;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.motion.camera.Camera;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.ColorFilter;
import br.com.etyllica.motion.filter.validation.MaxDimensionValidation;
import br.com.etyllica.motion.filter.validation.MinDensityValidation;
import br.com.etyllica.motion.filter.validation.MinDimensionValidation;
import examples.medium.application.area.AreaDrawer;

public class TrackingCameraColorApplication extends Application {

	private Camera cam;

	private ColorFilter blueFilter;

	//Blue Marker	
	private Color color = new Color(171, 112, 100);

	private int tolerance = 10;
	private int minDensity = 12;
	private int minDimension = 10;
	private int maxDimension = 100;

	private MinDensityValidation densityValidation;
	private MinDimensionValidation dimensionValidation;

	private boolean hide = false;
	private boolean pixels = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private Component screen;

	private List<Component> blueComponents;

	private BufferedLayer layer;

	public TrackingCameraColorApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingInfo = "Loading Images";

		screen = setupCamera();

		densityValidation = new MinDensityValidation(minDensity);
		dimensionValidation = new MinDimensionValidation(minDimension);

		blueFilter = new ColorFilter(screen.getW(), screen.getH(), color, tolerance);
		blueFilter.getSearchStrategy().addValidation(dimensionValidation);
		blueFilter.getSearchStrategy().addValidation(new MaxDimensionValidation(maxDimension));

		blueFilter.getSearchStrategy().addValidation(densityValidation);

		final int MAGIC_NUMBER = 3;//Higher = Faster and less precise

		blueFilter.getSearchStrategy().setBorder(MAGIC_NUMBER);
		blueFilter.getSearchStrategy().setStep(2);

		loadingInfo = "Configuring Filter";

		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}

	protected Component setupCamera() {
		cam = new CameraV4L4J(0);

		int w = cam.getBufferedImage().getWidth();
		int h = cam.getBufferedImage().getHeight();

		screen = new Component(0, 0, w, h);
		layer = new BufferedLayer(w, h);

		return screen;
	}

	int bx = 0;
	int by = 0;
	int bRadius = 0;

	private void reset(BufferedImage b) {
		layer.setBuffer(b);
		layer.flipHorizontal();

		blueComponents = blueFilter.filter(layer.getBuffer(), screen);

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
	public void updateMouse(PointerEvent event) {

		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			color = pickColor(event.getX(), event.getY());
			blueFilter.setColor(color);

			System.out.println(color.getRed());
			System.out.println(color.getGreen());
			System.out.println(color.getBlue());
			System.out.println("---------");
		}
	}

	private Color pickColor(int px, int py) {
		return new Color(layer.getBuffer().getRGB(px, py));
	}

	@Override
	public void updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.VK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.VK_J)){
			pixels = !pixels;
		}

		//Change Tolerance
		if(event.isKeyUp(KeyEvent.VK_EQUALS)) {
			tolerance++;
			blueFilter.setTolerance(tolerance);
		} else if(event.isKeyUp(KeyEvent.VK_MINUS)) {
			tolerance--;
			blueFilter.setTolerance(tolerance);
		}

		//Change Density
		if(event.isKeyUp(KeyEvent.VK_P)) {
			minDensity++;
			densityValidation.setDensity(minDensity);
		} else if(event.isKeyUp(KeyEvent.VK_O)) {
			minDensity--;
			densityValidation.setDensity(minDensity);
		}

		//Change Dimension
		if(event.isKeyUp(KeyEvent.VK_L)) {
			minDimension++;
			dimensionValidation.setDimension(minDimension);
		} else if(event.isKeyUp(KeyEvent.VK_K)) {
			minDimension--;
			dimensionValidation.setDimension(minDimension);
		}
	}

	@Override
	public void draw(Graphics g) {

		if(!hide){
			g.drawImage(layer.getBuffer(), xOffset, yOffset);
		}

		reset(cam.getBufferedImage());
		
		if(pixels) {

			g.setColor(color);
			g.fillRect(0, 0, 60, 80);

			g.setColor(Color.BLACK);

			g.drawString("Tol: "+Integer.toString(tolerance), 10, 80);
			g.drawString("Den: "+Integer.toString(minDensity), 10, 100);
			g.drawString("Dim: "+Integer.toString(minDimension), 10, 120);

			g.setAlpha(60);
			//drawFeaturedPoints(g, sampledFeature, Color.GREEN);
			g.setAlpha(100);

			g.setColor(Color.GREEN);

			Component c1 = null, c2 = null;
			if(blueComponents != null) {
				for(Component component:blueComponents) {
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
				AreaDrawer.drawMultiArea(g, c1.getCenter(), c2.getCenter());
			}

			g.setAlpha(50);
			g.fillCircle(bx, by, bRadius);
			g.resetOpacity();
		}
	}

}