package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.camera.FakeCamera;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.custom.wand.BorderFilter;
import br.com.etyllica.motion.custom.wand.MagicWandConvexFilter;
import br.com.etyllica.motion.features.Component;

public class MagicWandStatic extends Application{

	FakeCamera cam = new FakeCamera();

	private BorderFilter cornerFilter = new BorderFilter(0, 0);

	//private MagicWandBoxFilter filter = new MagicWandBoxFilter(0, 0);
	private MagicWandConvexFilter filter = new MagicWandConvexFilter(0, 0);

	private boolean hide = false;
	private boolean pixels = true;

	private int xOffset = 40;
	private int yOffset = 40;

	private final int IMAGES_TO_LOAD = 4;	

	private Component box;

	private double angle = 0;

	private Component feature;

	public MagicWandStatic(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Loading Images";

		for(int i=0;i<IMAGES_TO_LOAD;i++){
			cam.addImage("wand/wand"+Integer.toString(i)+".png");
		}

		loadingPhrase = "Configuring Filter";
		filter.setWandColor(Color.BLACK);
		filter.setTolerance(140);

		reset(cam.getBufferedImage());

		loading = 100;
	}

	private void reset(BufferedImage b){
		int w = b.getWidth();
		int h = b.getHeight();

		filter.setW(w);
		filter.setH(h);

		feature = cornerFilter.filter(b, new Component(w, h)).get(0);

		List<Component> result = filter.filter(b, feature);

		box = result.get(0);


		angle = filter.getAngle();
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

		g.setColor(Color.BLUE);

		if(feature.getPoints().size()>3){
			for(Ponto2D ponto: feature.getPoints()){
				g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);
			}

			drawBox(g, box);

			g.drawString("Angle = "+angle, 50, 30);

		}

	}

	private void drawBox(Graphic g, Component box){

		g.setColor(Color.RED);

		Ponto2D a = box.getPoints().get(0);
		Ponto2D b = box.getPoints().get(1);
		Ponto2D c = box.getPoints().get(2);
		Ponto2D d = box.getPoints().get(3);

		Ponto2D ac = new Ponto2D((a.getX()+c.getX())/2, (a.getY()+c.getY())/2);
		Ponto2D ab = new Ponto2D((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);

		Ponto2D bd = new Ponto2D((b.getX()+d.getX())/2, (b.getY()+d.getY())/2);
		Ponto2D cd = new Ponto2D((c.getX()+d.getX())/2, (c.getY()+d.getY())/2);

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

	private void drawLine(Graphic g, Ponto2D a, Ponto2D b){		
		g.drawLine(xOffset+(int)a.getX(), yOffset+(int)a.getY(), xOffset+(int)b.getX(), yOffset+(int)b.getY());		
	}

	private void drawPoint(Graphic g, Ponto2D point){
		g.fillCircle(xOffset+(int)point.getX(), yOffset+(int)point.getY(), 3);
	}


}
