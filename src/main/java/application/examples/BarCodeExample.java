package application.examples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.custom.BarCodeFilter;
import br.com.etyllica.motion.feature.Component;

public class BarCodeExample extends Application {

	private FakeCamera cam = new FakeCamera();

	private BarCodeFilter filter = new BarCodeFilter((int)w, (int)h);

	private boolean hide = false;
	private boolean pixels = true;

	private int xOffset = 40;
	private int yOffset = 40;

	private List<Component> result;
	
	private Component screen; 

	public BarCodeExample(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		screen = new Component(0, 0, w, h);
		
		filter.setBorder(2);

		loadingInfo = "Loading Images";

		cam.addImage("wand/wand6.png");

		loading = 25;
		loadingInfo = "Configuring Filter";
		
		filter = new BarCodeFilter(cam.getBufferedImage().getWidth(), cam.getBufferedImage().getHeight());

		reset(cam.getBufferedImage());

		loading = 100;
	}
	
	private void reset(BufferedImage b){
		
		loading = 65;
		loadingInfo = "Show Result";

		result = filter.filter(b, screen);

		loading = 70;
		loadingInfo = "Show Angle";
		
	}

	@Override
	public void updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.VK_RIGHT_ARROW)){
			cam.nextFrame();
			reset(cam.getBufferedImage());
		}

		else if(event.isKeyDown(KeyEvent.VK_LEFT_ARROW)){
			cam.previousFrame();
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.VK_H)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.VK_P)){
			pixels = !pixels;
		}

	}

	@Override
	public void draw(Graphic g) {

		g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		
		g.drawImage(cam.getBufferedImage(), xOffset, yOffset+200);

		int offset = 1;
		for(Component feature: result){

			drawBox(g, feature, offset%2*20);
			
			offset++;

		}

	}

	private void drawBox(Graphic g, Component box, int downOffset){

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

		g.drawString(Integer.toString((int)(d.getX()-a.getX())), xOffset+(int)d.getX()-12, yOffset+(int)d.getY()+20+downOffset);

	}

	private void drawLine(Graphic g, Point2D a, Point2D b){		
		g.drawLine(xOffset+(int)a.getX(), yOffset+(int)a.getY(), xOffset+(int)b.getX(), yOffset+(int)b.getY());		
	}

	private void drawPoint(Graphic g, Point2D point){
		g.fillCircle(xOffset+(int)point.getX(), yOffset+(int)point.getY(), 3);
	}

}
