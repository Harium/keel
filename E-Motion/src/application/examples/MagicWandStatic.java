package application.examples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.core.features.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.FloodFillSearch;
import br.com.etyllica.motion.modifier.EnvelopeModifier;

public class MagicWandStatic extends Application{

	private FakeCamera cam;

	private FloodFillSearch cornerFilter;

	private ColorStrategy colorStrategy;

	private EnvelopeModifier modifier;

	private boolean hide = false;
	private boolean pixels = true;

	private int xOffset = 40;
	private int yOffset = 40;

	private final int IMAGES_TO_LOAD = 7;

	private List<Component> features;

	public MagicWandStatic(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Loading Images";

		cam = new FakeCamera();

		for(int i=0;i<IMAGES_TO_LOAD;i++){
			cam.addImage("/wand/wand"+Integer.toString(i)+".png");
		}

		loading = 25;

		loadingPhrase = "Configuring Filter";

		int width = cam.getBufferedImage().getWidth();

		int height = cam.getBufferedImage().getHeight();

		loading = 40;

		colorStrategy = new ColorStrategy(Color.BLACK);
		colorStrategy.setTolerance(0x10);

		modifier = new EnvelopeModifier();

		cornerFilter = new FloodFillSearch(width, height);
		cornerFilter.setBorder(10);

		cornerFilter.setPixelStrategy(colorStrategy);

		cornerFilter.setComponentModifierStrategy(modifier);

		reset(cam.getBufferedImage());

		loading = 100;
	}

	private void reset(BufferedImage b){

		loading = 60;

		loadingPhrase = "Start Filter";

		features = cornerFilter.filter(b, new Component(0, 0, w, h));

		loading = 65;
		loadingPhrase = "Show Result";

		loading = 70;
		loadingPhrase = "Show Angle";
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

		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {

		g.drawImage(cam.getBufferedImage(), xOffset, yOffset);

		g.setColor(Color.BLACK);
		
		g.drawString("Angle = "+modifier.getAngle(), 50, 25);
		
		g.setColor(Color.BLUE);
		
		for(Component feature: features){

			for(Point2D ponto: feature.getPoints()){
				g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);
			}

			if(feature.getPoints().size()>3){	

				drawBox(g, feature);

				g.drawString("Points = "+feature.getPoints().size(), 50, 50);

			}

		}

	}

	private void drawBox(Graphic g, Component box){

		g.setColor(Color.RED);

		Point2D a = box.getPoints().get(0);
		Point2D b = box.getPoints().get(1);
		Point2D c = box.getPoints().get(2);
		Point2D d = box.getPoints().get(3);

		Point2D ac = new Point2D((a.getX()+c.getX())/2, (a.getY()+c.getY())/2);
		Point2D ab = new Point2D((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);

		Point2D bd = new Point2D((b.getX()+d.getX())/2, (b.getY()+d.getY())/2);
		Point2D cd = new Point2D((c.getX()+d.getX())/2, (c.getY()+d.getY())/2);

		drawLine(g, a, b);
		drawLine(g, a, c);

		drawLine(g, b, d);
		drawLine(g, c, d);

		drawPoint(g, a);
		drawPoint(g, b);
		drawPoint(g, c);
		drawPoint(g, d);

		g.setColor(Color.YELLOW);
		drawLine(g, ab, cd);
		drawPoint(g, ab);
		drawPoint(g, cd);

		g.setColor(Color.GREEN);
		drawLine(g, ac, bd);

		drawPoint(g, ac);
		drawPoint(g, bd);


		g.setColor(Color.BLACK);
		g.drawString("A", xOffset+(int)a.getX()-20, yOffset+(int)a.getY()-10);
		g.drawString("B", xOffset+(int)b.getX()+15, yOffset+(int)b.getY()-10);

		g.drawString("C", xOffset+(int)c.getX()-20, yOffset+(int)c.getY()+10);
		g.drawString("D", xOffset+(int)d.getX()+15, yOffset+(int)d.getY()+10);

	}

	private void drawLine(Graphic g, Point2D a, Point2D b){		
		g.drawLine(xOffset+(int)a.getX(), yOffset+(int)a.getY(), xOffset+(int)b.getX(), yOffset+(int)b.getY());		
	}

	private void drawPoint(Graphic g, Point2D point){
		g.fillCircle(xOffset+(int)point.getX(), yOffset+(int)point.getY(), 3);
	}


}
