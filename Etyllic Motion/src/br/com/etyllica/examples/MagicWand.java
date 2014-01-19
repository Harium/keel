package br.com.etyllica.examples;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.camera.CameraV4L4J;
import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.features.BoundingComponent;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.features.Wand;
import br.com.etyllica.motion.filter.color.ColorFilter;
import br.com.etyllica.motion.filter.wand.DegenarateBoxFilter;

public class MagicWand extends Application{

	private CameraV4L4J cam;
	
	private ColorFilter colorFilter = new ColorFilter();
	
	private DegenarateBoxFilter filter;
	//private BlackWandFilter filter;

	private boolean hide = false;
	private boolean pixels = true;

	private int xImage = 0;
	private int yImage = 0;
	
	private Component screen;
	
	public MagicWand(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {

		loadingPhrase = "Open Camera";
		
		cam = new CameraV4L4J(0);
		
		screen = new BoundingComponent(cam.getBufferedImage().getWidth(), cam.getBufferedImage().getHeight());
		
		loadingPhrase = "Setting Filter";
				
		colorFilter.setColor(new Color(0x25,0x27,0x60).getRGB());
		colorFilter.setTolerance(20);
		
		filter = new DegenarateBoxFilter();		

		loading = 100;
	}

	List<Component> result = new ArrayList<Component>();
	
	List<Wand> features = new ArrayList<Wand>();
	
	private void reset(BufferedImage b){
		
		Component feature = colorFilter.filter(b, screen).get(0);
		
		result = filter.filter(b, feature);
		
		features.clear();
		
		for(Component component: result){
			
			Wand wand = new Wand(component);
			wand.setAngle(filter.getAngle());
			wand.setDistance(filter.getDistance());
			
			features.add(wand);
		}
		
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		
		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)){
			//When mouse clicks with LeftButton, the color filter tries to find
			//the color we are clicking on
			colorFilter.setColor(cam.getBufferedImage().getRGB((int)event.getX(), (int)event.getY()));
						
		}
		
		return GUIEvent.NONE;
	}
	
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

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

		BufferedImage b = cam.getBufferedImage();
		
		reset(b);
		
		g.drawImage(b, xImage, yImage);

		Wand biggest = null;
		double maiorNumeroPontos = 0;
		
		for(Wand wand: features){
			
			g.setColor(wand.getColor());
		
			for(Point2D ponto: wand.getPoints()){
				g.fillCircle(xImage+(int)ponto.getX(), yImage+(int)ponto.getY(), 5);
				
			}
			
			if(wand.getDistance()>maiorNumeroPontos){
				biggest = wand;
				maiorNumeroPontos = wand.getDistance();
			}
			
			//drawBox(g, wand);

			int px = (int)wand.getPoints().get(0).getX();
			int py = (int)wand.getPoints().get(0).getY();
			g.setColor(Color.WHITE);
			//g.drawShadow(px, py, (int)wand.getAngle()+"°");
			g.drawShadow(px, py, Double.toString(wand.getDistance()));
			
		}
		
		if(biggest!=null){
			g.drawShadow(50, 40, "Ângulo = "+biggest.getAngle()+"°");
			g.drawShadow(50, 60, "Distance = "+biggest.getDistance());
			drawBox(g, biggest);
		}

	}

	private void drawBox(Graphic g, Component box){

		if(box.getPoints().size()<4){
			return;
		}
		
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
		g.drawString("A", xImage+(int)a.getX()-20, yImage+(int)a.getY()-10);
		g.drawString("B", xImage+(int)b.getX()+15, yImage+(int)b.getY()-10);

		g.drawString("C", xImage+(int)c.getX()-20, yImage+(int)c.getY()+10);
		g.drawString("D", xImage+(int)d.getX()+15, yImage+(int)d.getY()+10);

	}

	private void drawLine(Graphic g, Point2D a, Point2D b){		
		g.drawLine(xImage+(int)a.getX(), yImage+(int)a.getY(), xImage+(int)b.getX(), yImage+(int)b.getY());		
	}

	private void drawPoint(Graphic g, Point2D point){
		g.fillCircle(xImage+(int)point.getX(), yImage+(int)point.getY(), 3);
	}

}
