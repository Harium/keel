package test;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.camera.CameraV4L4J;
import br.com.etyllica.camera.FakeCamera;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.linear.Ponto2D;
import br.com.etyllica.motion.custom.face.SkinBorderFilter;
import br.com.etyllica.motion.custom.polygon.QuickHullFilter;
import br.com.etyllica.motion.custom.wand.BorderFilter;
import br.com.etyllica.motion.custom.wand.MagicWandConvexFilter;
import br.com.etyllica.motion.features.Component;

public class FaceSampledStatic extends Application{

	private FakeCamera cam = new FakeCamera();

	private BorderFilter colorFilter = new BorderFilter(0, 0);

	private MagicWandConvexFilter filter = new MagicWandConvexFilter(0, 0);
	
	private QuickHullFilter quickFilter = new QuickHullFilter(w, h);

	private boolean hide = false;
	private boolean pixels = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private final int IMAGES_TO_LOAD = 50;	

	private Component sampledFeature;

	private Polygon sampledPolygon = new Polygon();

	public FaceSampledStatic(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Loading Images";

		for(int i=1;i<=IMAGES_TO_LOAD;i++){
			loading = i;
			cam.addImage("skin/skin"+Integer.toString(i)+".jpg");
		}		

		colorFilter.setColor(Color.BLACK.getRGB());
		colorFilter.setTolerance(0x50);
		colorFilter.setBorder(4);
		colorFilter.setStep(4);

		loadingPhrase = "Configuring Filter";
		filter.setWandColor(Color.BLACK);
		filter.setTolerance(0);

		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}

	private void reset(BufferedImage b){
		int w = b.getWidth();
		int h = b.getHeight();

		filter.setW(w);
		filter.setH(h);

		//Sampled
		sampledFeature = colorFilter.filter(b, new Component(w, h)).get(0);

		sampledPolygon.reset();

		//TODO Separate polygons
		
		quickFilter.filter(b, sampledFeature);
		
		sampledPolygon = quickFilter.getPolygon();
		
	}

	private List<Component> separateComponents(Component feature){

		List<Component> result = new ArrayList<Component>();

		List<Ponto2D> points = new ArrayList<Ponto2D>(feature.getPoints());

		Component currentComponent = new Component(0, 0);		

		currentComponent.add(points.get(0));

		int p = 1;

		Ponto2D pt = points.get(p);		

		final int radius = 20;

		//while(points.size()>0){

			for(int i=1;i<points.size();i++){

				Ponto2D q = points.get(i);

				if(insideCircle((int)pt.getX(), (int)pt.getY(), radius, (int)q.getX(), (int)q.getY())){
					currentComponent.add(q);
					points.remove(i);
					continue;
				}

			}
			
		//}


		return result;


	}

	private boolean insideCircle(int cx, int cy, int radius, int px, int py){

		int difX = (x - cx)*(x - cx);
		int difY = (x - cx)*(x - cx);

		return difX + difY < radius*radius;

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

		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(Color.BLUE);

		g.setAlpha(60);
		drawFeaturedPoints(g, sampledFeature, Color.GREEN);
		g.setAlpha(100);

		g.setColor(Color.GREEN);
		g.drawPolygon(sampledPolygon);

	}

	private void drawFeaturedPoints(Graphic g, Component feature, Color color){

		for(Ponto2D ponto: feature.getPoints()){

			g.setColor(color);
			g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);

			g.setColor(Color.WHITE);
			g.drawCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 18);

		}

	}

}
