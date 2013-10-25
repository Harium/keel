package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.camera.CameraV4L4J;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.custom.wand.MagicWandBoxFilter;
import br.com.etyllica.motion.custom.wand.model.Wand;
import br.com.etyllica.motion.features.Component;

public class MagicWand extends Application{

	private CameraV4L4J cam;
	
	private MagicWandBoxFilter filter;

	private boolean hide = false;
	private boolean pixels = true;

	private int xImage = 0;
	private int yImage = 0;
	
	public MagicWand(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void load() {

		loadingPhrase = "Open Camera";
		
		cam = new CameraV4L4J(0);
				
		loadingPhrase = "Setting Filter";
		filter = new MagicWandBoxFilter(cam.getBufferedImage().getWidth(), cam.getBufferedImage().getHeight());
		
		filter.setWandColor(new Color(0x36,0x38,0x35));
		filter.setTolerance(20);

		loading = 100;
	}

	List<Component> result = new ArrayList<Component>();
	
	List<Wand> features = new ArrayList<Wand>();
	
	private void reset(BufferedImage b){
		
		int w = b.getWidth();
		int h = b.getHeight();

		filter.setW(w);
		filter.setH(h);
		
		result = filter.filter(b, new Component(w, h));
		
		features.clear();
		for(Component component: result){
			
			Wand wand = new Wand(component);
			wand.setAngle(filter.getAngle());
			
			features.add(wand);
		}
		
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
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
		int maiorNumeroPontos = 0;
		
		for(Wand wand: features){
			
			g.setColor(wand.getColor());
		
			for(Ponto2D ponto: wand.getPoints()){
				g.fillCircle(xImage+(int)ponto.getX(), yImage+(int)ponto.getY(), 5);
				
			}
			
			if(wand.getPoints().size()>maiorNumeroPontos){
				biggest = wand; 
			}
			
			drawBox(g, wand);

			int px = (int)wand.getPoints().get(0).getX();
			int py = (int)wand.getPoints().get(0).getY();
			g.setColor(Color.WHITE);
			g.drawShadow(px, py, (int)wand.getAngle()+"°");
			
		}
		
		if(biggest!=null){
			g.drawShadow(50, 40, "Ângulo = "+biggest.getAngle()+"°");
		}

	}

	private void drawBox(Graphic g, Component box){

		if(box.getPoints().size()<4){
			return;
		}
		
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
		g.drawString("A", xImage+(int)a.getX()-20, yImage+(int)a.getY()-10);
		g.drawString("B", xImage+(int)b.getX()+15, yImage+(int)b.getY()-10);

		g.drawString("C", xImage+(int)c.getX()-20, yImage+(int)c.getY()+10);
		g.drawString("D", xImage+(int)d.getX()+15, yImage+(int)d.getY()+10);

	}

	private void drawLine(Graphic g, Ponto2D a, Ponto2D b){		
		g.drawLine(xImage+(int)a.getX(), yImage+(int)a.getY(), xImage+(int)b.getX(), yImage+(int)b.getY());		
	}

	private void drawPoint(Graphic g, Ponto2D point){
		g.fillCircle(xImage+(int)point.getX(), yImage+(int)point.getY(), 3);
	}


}
