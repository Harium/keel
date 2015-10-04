package examples.medium.skin;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.MouseButton;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.core.strategy.SearchFilter;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.SkinColorFilter;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.skin.SkinColorKovacNewStrategy;
import br.com.etyllica.motion.filter.validation.MinDimensionValidation;

public class SimpleSkinStrategyApplication extends Application {

	private FakeCamera cam = new FakeCamera();
	private final int IMAGES_TO_LOAD = 90;

	private SkinColorFilter skinFilter;

	private List<Component> skinComponents;

	private Component screen;

	private Color color = Color.BLACK;

	/*private HullModifier<HullComponent> quickHull;
	private PathCompressionModifier pathCompressionModifier;

	private List<String> geometryText = new ArrayList<String>();		
	private List<List<Point2D>> convexHull = new ArrayList<List<Point2D>>();*/

	private boolean drawPoints = false;
	private boolean leftPoints = true;

	public SimpleSkinStrategyApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		loading = 0;

		loadingInfo = "Loading Images";

		for(int i=1;i<=IMAGES_TO_LOAD;i++) {
			loading = i;

			cam.addImage("skin/skin"+Integer.toString(i)+".jpg");
		}

		loadingInfo = "Configuring Filter";

		loading = 25;
		reset();

		/*System.out.println("skin: "+skinComponents.size());
		quickHull = new FastConvexHullModifier();
		pathCompressionModifier = new PathCompressionModifier(5);

		loading = 31;

		for(Component component : skinComponents) {
			classifyRegion(component);
		}*/

		loading = 50;
	}

	/*private void classifyRegion(Component region) {

		List<Point2D> list = pathCompressionModifier.modify(quickHull.modify(region));
		//List<Point2D> list = quickHull.modify(region).getPoints();

		Point2D center = region.getCenter();
		Color color = new Color(cam.getBufferedImage().getRGB((int)center.getX(), (int)center.getY())); 

		String colorText = ColorClassifier.getColorName(color.getRed(), color.getGreen(), color.getBlue());

		String form = PolygonClassifier.indentifyRegion(list);

		String text = colorText+" "+form;

		geometryText.add(text);
		convexHull.add(list);
	}*/

	@Override
	public void draw(Graphic g) {
		//g.setAlpha(100);
		g.drawImage(cam.getBufferedImage(), 0, 0);

		//g.setAlpha(90);

		//Draw a red line around the components

		for(int i = 0; i < skinComponents.size(); i++) {
			Component component = skinComponents.get(i);

			//g.setStroke(new BasicStroke(3f));

			g.setColor(color);
			g.drawRect(component.getRectangle());

			if(drawPoints) {
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
		}
	}

	private Color randomColor() {
		int r = new Random().nextInt(255);
		int g = new Random().nextInt(255);
		int b = new Random().nextInt(255);

		return new Color(r,g,b);
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.VK_RIGHT)) {
			cam.nextFrame();
			reset();
		} else if(event.isKeyDown(KeyEvent.VK_LEFT)) {
			cam.previousFrame();
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

		return null;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

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

		return GUIEvent.NONE;
	}

	private void reset() {
		//Define the area to search for elements
		int w = cam.getBufferedImage().getWidth();
		int h = cam.getBufferedImage().getHeight();

		screen = new Component(0, 0, w, h);
		//skinFilter = new SkinColorFilter(w, h, new SkinColorEllipticStrategy());
		//skinFilter = new SkinColorFilter(w, h, new SkinColorKovacStrategy());
		skinFilter = new SkinColorFilter(w, h, new SkinColorKovacNewStrategy());
		
		SearchFilter filter = skinFilter.getSearchStrategy();
		filter.setStep(2);
		filter.setBorder(4);
		
		//Remove components smaller than 20x20
		skinFilter.addValidation(new MinDimensionValidation(20));

		skinComponents = skinFilter.filter(cam.getBufferedImage(), screen);

		color = randomColor();
	}
}
