package br.com.etyllica.examples;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import br.com.etyllica.camera.FakeCamera;
import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.gui.spinner.IntegerSpinner;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.features.Component;
import br.com.etyllica.motion.filter.color.BorderFilter;
import br.com.etyllica.motion.filter.face.SkinBorderFilter;
import br.com.etyllica.motion.filter.polygon.NoiseFilter;
import br.com.etyllica.motion.filter.polygon.NoiseQuickHullFilter;
import br.com.etyllica.util.SVGColor;

public class FaceSampledMultiFilterStatic extends Application{

	private FakeCamera cam = new FakeCamera();

	private BorderFilter blackFilter = new BorderFilter(0, 0);

	private SkinBorderFilter skinFilter = new SkinBorderFilter(0, 0);

	private NoiseFilter quickFilter = new NoiseFilter((int)w, (int)h);

	private NoiseQuickHullFilter quickMergeFilter = new NoiseQuickHullFilter((int)w, (int)h);

	private boolean hide = false;
	private boolean pixels = true;
	private boolean drawCleanedOnly = false;
	private boolean drawBox = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private final int IMAGES_TO_LOAD = 50;	

	private Component blackSampledFeature;
	private Component skinFeature;

	private Polygon blackPolygon = new Polygon();
	private Polygon skinPolygon = new Polygon();

	private Polygon merged = new Polygon();

	private final int DEFAULT_STEP = 4;

	private final int DEFAULT_BORDER = DEFAULT_STEP*2;

	//private final int NOISE_RADIUS = DEFAULT_STEP*5;

	private int noiseRadius = 50;
	private int minNeighboor = 3;
	private int maxNeighboor = 5;

	private int tolerance = 50;

	private int skinNoiseRadius = 45;
	private int skinMinNeighboor = 1;
	private int skinMaxNeighboor = 6;

	private int skinTolerance = 5;

	IntegerSpinner skinMinNeighboorSpinner;

	private Component allPoints;	

	public FaceSampledMultiFilterStatic(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingPhrase = "Loading Images";

		for(int i=1;i<=IMAGES_TO_LOAD;i++){
			loading = i;
			cam.addImage("skin/skin"+Integer.toString(i)+".jpg");
		}

		loadingPhrase = "Configuring Filter";
		blackFilter.setColor(Color.BLACK.getRGB());
		blackFilter.setTolerance(tolerance);
		//border: 4 and step: 4
		blackFilter.setBorder(DEFAULT_BORDER);
		blackFilter.setStep(DEFAULT_STEP);

		skinFilter.setTolerance(tolerance-45);
		skinFilter.setBorder(DEFAULT_BORDER);
		skinFilter.setStep(DEFAULT_STEP);

		loading = 60;
		reset(cam.getBufferedImage());

		IntegerSpinner skinMinNeighboorSpinner = new IntegerSpinner(640, 0, 160, 40);
		skinMinNeighboorSpinner.setMinValue(0);
		skinMinNeighboorSpinner.setMaxValue(20);
		skinMinNeighboorSpinner.setValue(1);
		add(skinMinNeighboorSpinner);

		loading = 100;
	}

	private void reset(BufferedImage b){
		int w = b.getWidth();
		int h = b.getHeight();

		blackFilter.setTolerance(tolerance);

		quickFilter.setRadius(noiseRadius);
		quickFilter.setMinNeighboors(minNeighboor);
		quickFilter.setMaxNeighboors(maxNeighboor);
		//quickFilter.setRadius(20);

		//Sampled
		blackSampledFeature = blackFilter.filter(b, new Component(w, h)).get(0);
		blackPolygon.reset();
		quickFilter.filter(b, blackSampledFeature).get(0);
		blackPolygon = new Polygon(quickFilter.getPolygon().xpoints, quickFilter.getPolygon().ypoints, quickFilter.getPolygon().npoints);

		quickFilter.setRadius(skinNoiseRadius);
		quickFilter.setMinNeighboors(skinMinNeighboor);
		quickFilter.setMaxNeighboors(skinMaxNeighboor);

		skinFilter.setTolerance(skinTolerance);

		skinFeature = skinFilter.filter(b, new Component(w, h)).get(0);
		skinPolygon.reset();
		quickFilter.filter(b, skinFeature).get(0);
		skinPolygon = new Polygon(quickFilter.getPolygon().xpoints, quickFilter.getPolygon().ypoints, quickFilter.getPolygon().npoints);

		quickMergeFilter.setRadius(noiseRadius);
		quickMergeFilter.setMinNeighboors(1);
		quickMergeFilter.setMaxNeighboors(2);

		mergeAll();

	}

	private void mergeAll(){

		allPoints = new Component(0, 0);
		allPoints.mergePolygon(skinPolygon);
		allPoints.mergePolygon(blackPolygon);

		merged.reset();
		quickMergeFilter.filter(null, allPoints).get(0);
		merged = new Polygon(quickMergeFilter.getPolygon().xpoints, quickMergeFilter.getPolygon().ypoints, quickMergeFilter.getPolygon().npoints);

	}


	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		//System.out.println("updateKeyBoard "+event.getKey()+" "+event.getState());

		if(event.isKeyDown(KeyEvent.TSK_SETA_DIREITA)){
			cam.nextFrame();
			reset(cam.getBufferedImage());
		}

		else if(event.isKeyDown(KeyEvent.TSK_SETA_ESQUERDA)){
			cam.previousFrame();
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_Y)){
			hide = !hide;
		}

		if(event.isKeyDown(KeyEvent.TSK_P)){
			pixels = !pixels;
		}

		if(event.isKeyDown(KeyEvent.TSK_U)){
			drawCleanedOnly = !drawCleanedOnly;
		}

		if(event.isKeyDown(KeyEvent.TSK_I)){
			drawBox = !drawBox;
		}

		if(event.isKeyDown(KeyEvent.TSK_A)){
			noiseRadius++;			
			System.out.println("RadiusHull = "+noiseRadius);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_S)){
			noiseRadius--;
			System.out.println("RadiusHull = "+noiseRadius);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_Z)){
			tolerance++;
			System.out.println("tolerance = "+tolerance);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_X)){
			tolerance--;
			System.out.println("tolerance = "+tolerance);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_D)){
			minNeighboor++;
			System.out.println("minNeighboor = "+minNeighboor);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_F)){
			minNeighboor--;
			System.out.println("minNeighboor = "+minNeighboor);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_C)){
			maxNeighboor++;
			System.out.println("maxNeighboor = "+maxNeighboor);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_V)){
			maxNeighboor--;
			System.out.println("maxNeighboor = "+maxNeighboor);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_G)){
			skinNoiseRadius++;
			System.out.println("skinNoiseRadius = "+skinNoiseRadius);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_H)){
			skinNoiseRadius--;
			System.out.println("skinNoiseRadius = "+skinNoiseRadius);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_B)){
			skinTolerance++;
			System.out.println("skinTolerance = "+skinTolerance);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_N)){
			skinTolerance--;
			System.out.println("skinTolerance = "+skinTolerance);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_J)){
			skinMinNeighboor++;
			System.out.println("skinMinNeighboor = "+skinMinNeighboor);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_K)){
			skinMinNeighboor--;
			System.out.println("skinMinNeighboor = "+skinMinNeighboor);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_M)){
			skinMaxNeighboor++;
			System.out.println("skinMaxNeighboor = "+skinMaxNeighboor);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_VIRGULA)){
			skinMaxNeighboor--;
			System.out.println("skinMaxNeighboor = "+skinMaxNeighboor);
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_SPACE)){
			System.out.println("------------------------------");
			System.out.println("noiseRadius = "+noiseRadius+";");
			System.out.println("tolerance = "+tolerance+";");
			System.out.println("minNeighboor = "+minNeighboor+";");
			System.out.println("maxNeighboor = "+maxNeighboor+";");
			System.out.println(" ");
			System.out.println("skinNoiseRadius = "+skinNoiseRadius+";");
			System.out.println("skinTolerance = "+skinTolerance+";");
			System.out.println("skinMinNeighboor = "+skinMinNeighboor+";");
			System.out.println("skinMaxNeighboor = "+skinMaxNeighboor+";");
			System.out.println("------------------------------");
		}

		//Presets
		if(event.isKeyDown(KeyEvent.TSK_1)){

			noiseRadius = 117;
			tolerance = 50;
			minNeighboor = 16;
			maxNeighboor = 30;

			skinNoiseRadius = 55;
			skinTolerance = 5;
			skinMinNeighboor = 4;
			skinMaxNeighboor = 8;

			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_2)){

			noiseRadius = 117;
			tolerance = 68;
			minNeighboor = 7;
			maxNeighboor = 12;

			skinNoiseRadius = 54;
			skinTolerance = 0;
			skinMinNeighboor = 7;
			skinMaxNeighboor = 9;

			reset(cam.getBufferedImage());
		}


		if(event.isKeyDown(KeyEvent.TSK_3)){

			noiseRadius = 117;
			tolerance = 60;
			minNeighboor = 7;
			maxNeighboor = 29;

			skinNoiseRadius = 54;
			skinTolerance = 0;
			skinMinNeighboor = 6;
			skinMaxNeighboor = 9;
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_4)){

			noiseRadius = 55;
			tolerance = 75;
			minNeighboor = 4;
			maxNeighboor = 29;

			skinNoiseRadius = 66;
			skinTolerance = 15;
			skinMinNeighboor = 7;
			skinMaxNeighboor = 19;
			reset(cam.getBufferedImage());

		}

		if(event.isKeyDown(KeyEvent.TSK_5)){
			noiseRadius = 49;
			tolerance = 154;
			minNeighboor = 3;
			maxNeighboor = 27;

			skinNoiseRadius = 34;
			skinTolerance = 21;
			skinMinNeighboor = 5;
			skinMaxNeighboor = 24;
			reset(cam.getBufferedImage());
		}

		if(event.isKeyDown(KeyEvent.TSK_6)){
			noiseRadius = 55;
			tolerance = 79;
			minNeighboor = 4;
			maxNeighboor = 29;

			skinNoiseRadius = 66;
			skinTolerance = 15;
			skinMinNeighboor = 8;
			skinMaxNeighboor = 19;
		}

		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {

		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		//drawPolygon(g, lightDirection, whitePolygon, SVGColor.ORANGE);

		drawPolygon(g, blackPolygon, SVGColor.WHITE, noiseRadius);

		drawPolygon(g, skinPolygon, SVGColor.BLUE_VIOLET, skinNoiseRadius);

		//drawPolygon(g, merged, SVGColor.RED);
		drawMerged(g);

		/*drawComponentPolygon(g, blackSampledFeature, blackPolygon, SVGColor.WHITE);

		drawComponentPolygon(g, skinFeature, skinPolygon, SVGColor.BLUE_VIOLET);*/

	}

	private void drawComponentPolygon(Graphic g, Component component, Polygon p, Color color){

		g.setColor(SVGColor.BLACK);
		g.setBasicStroke(3);
		g.drawPolygon(p);

		g.setColor(color);
		g.setBasicStroke(1);
		g.drawPolygon(p);

		for(Point2D ponto: component.getPoints()){

			g.setColor(color);
			g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);

			g.setColor(Color.WHITE);
			g.drawCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), noiseRadius);

		}

	}

	private void drawPolygon(Graphic g, Polygon p, Color color, int radius){

		/*g.setColor(SVGColor.BLACK);
		g.setBasicStroke(3);
		g.drawPolygon(p);

		g.setColor(color);
		g.setBasicStroke(1);
		g.drawPolygon(p);*/

		for(int i=0;i<p.npoints;i++){

			Point2D ponto = new Point2D(p.xpoints[i], p.ypoints[i]);

			g.setColor(color);
			g.fillCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), 5);

			if(i%3==0){
				g.setColor(Color.WHITE);
				g.drawCircle(xOffset+(int)ponto.getX(), yOffset+(int)ponto.getY(), radius);
			}
		}

	}

	private void drawMerged(Graphic g){
		g.setColor(SVGColor.BLACK);
		g.setBasicStroke(3);
		g.drawPolygon(merged);

		g.setColor(SVGColor.RED);
		g.setBasicStroke(1);
		g.drawPolygon(merged);

		int px = 0, py = 0, pr = 0; 
		int pn = merged.npoints;

		if(pn==0){
			pn=1;
		}

		for(int i=0;i<merged.npoints;i++){

			px += merged.xpoints[i];
			py += merged.ypoints[i];

		}

		//g.setColor(Color.BLACK);
		//g.fillOvalCenter(xOffset+px/pn, yOffset+py/pn, 80, 100);

		g.setColor(Color.BLACK);
		g.fillCircle(xOffset+px/pn, yOffset+py/pn, 20);

		g.setColor(Color.WHITE);
		g.fillCircle(xOffset+px/pn, yOffset+py/pn, 10);

	}

}
