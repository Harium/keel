package examples.medium.skin;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.MouseButton;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.camera.Camera;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.core.strategy.SearchFilter;
import br.com.etyllica.motion.custom.AverageColorFilter;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.ExpandableColorFilter;
import br.com.etyllica.motion.filter.HardColorFilter;
import br.com.etyllica.motion.filter.SkinColorFilter;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.skin.SkinColorKovacNewStrategy;
import br.com.etyllica.motion.filter.validation.MinDimensionValidation;

public class SimpleFaceFinderApplication extends Application {

	protected Camera cam = new FakeCamera();
	private final int IMAGES_TO_LOAD = 20;

	private SkinColorFilter skinFilter;

	protected Component bestCandidate;
	private List<Component> skinComponents;
	private List<Component> darkComponents;
	private Map<Component, Integer> counts = new HashMap<Component, Integer>();
	protected List<Component> faceComponents = new ArrayList<Component>();

	private Component screen;

	private Color color = Color.BLACK;

	private boolean drawPoints = false;
	private boolean leftPoints = true;

	public SimpleFaceFinderApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		loading = 0;

		loadingInfo = "Loading Images";
		initCamera();
		loadingInfo = "Configuring Filter";

		loading = 25;
		reset();
		loading = 50;
	}

	protected void initCamera() {
		for(int i=1;i<=IMAGES_TO_LOAD;i++) {
			loading = i;

			((FakeCamera)cam).addImage("skin/skin"+Integer.toString(i)+".jpg");
		}
	}

	@Override
	public void updateKeyboard(KeyEvent event) {
		if(event.isKeyDown(KeyEvent.VK_RIGHT)) {
			((FakeCamera)cam).nextFrame();
			reset();
		} else if(event.isKeyDown(KeyEvent.VK_LEFT)) {
			((FakeCamera)cam).previousFrame();
			reset();
		} else if(event.isKeyDown(KeyEvent.VK_SPACE)) {
			drawPoints = !drawPoints;
		}

		if(event.isKeyDown(KeyEvent.VK_1)) {
			leftPoints = true;
		}

		if(event.isKeyDown(KeyEvent.VK_2)) {
			leftPoints = false;
		}
	}

	@Override
	public void updateMouse(PointerEvent event) {

		if(event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {
			int x = event.getX();
			int y = event.getY();

			BufferedImage buffer = cam.getBufferedImage();

			if(x<buffer.getWidth()&&y<buffer.getHeight()) {

				int rgb = buffer.getRGB(x, y);
				final int R = ColorStrategy.getRed(rgb);
				final int G = ColorStrategy.getGreen(rgb);
				final int B = ColorStrategy.getBlue(rgb);

				System.out.println(R+" "+G+" "+B+" RG_MOD="+(R-G)+" R-B="+(R-B));
			}
		}
	}

	protected void reset() {

		BufferedImage image = cam.getBufferedImage();

		//Define the area to search for elements
		int w = image.getWidth();
		int h = image.getHeight();

		screen = new Component(0, 0, w, h);
		skinFilter = new SkinColorFilter(w, h, new SkinColorKovacNewStrategy());
		HardColorFilter colorFilter = new HardColorFilter(w, h, new Color(40,40,40), 25);

		SearchFilter filter = skinFilter.getSearchStrategy();
		filter.setStep(2);
		filter.setBorder(20);

		//Remove components smaller than 20x20
		skinFilter.addValidation(new MinDimensionValidation(20));
		skinComponents = skinFilter.filter(image, screen);

		colorFilter.addValidation(new MinDimensionValidation(3));
		darkComponents = colorFilter.filter(image, screen);

		//Evaluate components
		//validateComponents();
		bestCandidate = evaluateComponent(skinComponents);

		Color faceColor = AverageColorFilter.filter(image, bestCandidate);

		ExpandableColorFilter featureFilter = new ExpandableColorFilter(w, h, faceColor, 30);
		//featureFilter.getSearchStrategy().setBorder(2);
		featureFilter.getSearchStrategy().setStep(4);
		featureFilter.addValidation(new MinDimensionValidation(2));

		faceComponents = featureFilter.filter(image, bestCandidate);

		//System.out.println("Fc "+faceComponents.size());
		color = randomColor();
	}

	private void validateComponents() {
		for (int i=skinComponents.size()-1;i>=0;i--) {
			Component component = skinComponents.get(i);

			//Vertical trim component
			//component = trim(component);

			//Remove components near from left border
			if(component.getX() < 20+10) {
				skinComponents.remove(i);
				continue;
			}

			if(component.getX()+component.getW() > h-10) {
				skinComponents.remove(i);
				continue;
			}
		}
	}

	private Component evaluateComponent(List<Component> components) {
		int higher = 0;
		Component faceCandidate = components.get(0);

		for(Component component:components) {
			int count = 0;
			for(Component dc:darkComponents) {
				if(component.colide(dc)) {
					count++;
				}
			}
			if(count>higher) {
				higher = count;
				faceCandidate = component;
			}
			counts.put(component, count);
		}

		return faceCandidate;
	}

	private Color randomColor() {
		int r = new Random().nextInt(255);
		int g = new Random().nextInt(255);
		int b = new Random().nextInt(255);

		return new Color(r,g,b);
	}

	@Override
	public void draw(Graphic g) {	
		g.drawImage(cam.getBufferedImage(), 0, 0);

		g.setColor(color);
		drawComponent(g, bestCandidate);

		g.setColor(Color.RED);
		for(Component feature: faceComponents) {
			drawAllPoints(g, feature);
			g.drawRect(feature.getRectangle());
		}

		//Draw a red line around the components
		//drawComponents(g);

		//Draw dark components
		/*g.setStroke(new BasicStroke(3f));
		g.setColor(Color.BLACK);

		for(Component component:darkComponents) {
			g.drawRect(component.getRectangle());
		}*/
	}

	protected void drawComponents(Graphic g) {
		for(int i = 0; i < skinComponents.size(); i++) {
			Component component = skinComponents.get(i);

			g.setColor(color);
			drawComponent(g, component);
		}
	}

	protected void drawComponent(Graphic g, Component component) {
		//g.setStroke(new BasicStroke(3f));
		g.drawRect(component.getRectangle());

		g.setColor(Color.BLACK);

		int count = counts.get(component);

		g.drawString(component.getRectangle(), Integer.toString(count));

		if(drawPoints) {
			drawPoints(g, component);
		}
	}

	public void drawPoints(Graphic g, Component component) {
		for(Point2D point: component.getPoints()) {

			if(leftPoints) {
				if(point.getX()<w/2) {
					g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);
				}
			} else if(point.getX()>=w/2) {
				g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);	
			}
		}
	}

	public void drawAllPoints(Graphic g, Component component) {
		for(Point2D point: component.getPoints()) {
			g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);
		}
	}
}
