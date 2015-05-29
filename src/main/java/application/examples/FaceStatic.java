package application.examples;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.graphics.SVGColor;
import br.com.etyllica.motion.camera.FakeCamera;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.color.CrossSearch;
import br.com.etyllica.motion.filter.color.SkinColorStrategy;
import br.com.etyllica.motion.filter.search.TriangularSearch;
import br.com.etyllica.motion.modifier.EnvelopeModifier;
import br.com.etyllica.motion.modifier.hull.QuickHullModifier;

public class FaceStatic extends Application {

	private FakeCamera cam = new FakeCamera();

	private CrossSearch blackFilter = new CrossSearch();
	
	private CrossSearch whiteFilter = new CrossSearch();
	
	private TriangularSearch skinFilter;
	
	private boolean hide = false;
	private boolean pixels = true;
	private boolean drawCleanedOnly = false;
	private boolean drawBox = true;

	private int xOffset = 0;
	private int yOffset = 0;

	private final int IMAGES_TO_LOAD = 50;	

	private Component blackSampledFeature;
	
	private Component lightDirection;
	
	private List<Component> skinFeatures;

	private Polygon blackPolygon = new Polygon();
	
	private Polygon whitePolygon = new Polygon();
	
	private Component screen;
	
	private QuickHullModifier modifier;

	public FaceStatic(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		loadingInfo = "Loading Images";

		for(int i=1;i<=IMAGES_TO_LOAD;i++){
			loading = i;
			
			cam.addImage("skin/skin"+Integer.toString(i)+".jpg");
		}
		
		int width = cam.getBufferedImage().getWidth();
		int height = cam.getBufferedImage().getHeight();
		
		loadingInfo = "Configuring Filter";
		
		modifier = new QuickHullModifier();
		
		ColorStrategy blackColorFilter = new ColorStrategy(Color.BLACK.getRGB());
		blackColorFilter.setTolerance(0x50);
		
		blackFilter.setPixelStrategy(blackColorFilter);
		blackFilter.setComponentModifierStrategy(modifier);
		
		//border: 4 and step: 4
		blackFilter.setBorder(8);
		blackFilter.setStep(4);
		
		//White Color
		ColorStrategy whiteColorFilter = new ColorStrategy(Color.WHITE.getRGB());
		whiteColorFilter.setTolerance(0x64);
		
		whiteFilter.setPixelStrategy(whiteColorFilter);
		whiteFilter.setComponentModifierStrategy(modifier);
		
		whiteFilter.setBorder(4);
		whiteFilter.setStep(4);
		
		skinFilter = new TriangularSearch(width, height);
		skinFilter.setBorder(4);
		skinFilter.setPixelStrategy(new SkinColorStrategy());
		skinFilter.setComponentModifierStrategy(new EnvelopeModifier());
		
		loading = 60;
		reset(cam.getBufferedImage());

		loading = 100;
	}

	private void reset(BufferedImage b){
		int w = b.getWidth();
		int h = b.getHeight();
		
		screen = new Component(w, h);

		//Sampled
		blackSampledFeature = blackFilter.filterFirst(b, screen);
		blackPolygon.reset();

		blackPolygon = blackSampledFeature.getPolygon();
		
		
		//White Component
		lightDirection = whiteFilter.filterFirst(b, screen);
		whitePolygon.reset();
		
		whitePolygon = lightDirection.getPolygon();
		
		skinFeatures = skinFilter.filter(b, screen);
				
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

		if(event.isKeyDown(KeyEvent.TSK_C)){
			drawCleanedOnly = !drawCleanedOnly;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_B)){
			drawBox = !drawBox;
		}


		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {
		
		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(Color.BLUE);

		g.setColor(SVGColor.BLACK);
		g.setBasicStroke(2);
		g.drawPolygon(blackPolygon);
		
		g.drawRect(blackSampledFeature.getRectangle());
		
		g.setColor(SVGColor.WHITE);
		g.setBasicStroke(1);
		
		g.setColor(SVGColor.ORANGE);
		g.drawPolygon(whitePolygon);
		
		g.drawRect(lightDirection.getRectangle());
		
		g.setBasicStroke(2);
		for(Component skin: skinFeatures){
			
			g.setColor(SVGColor.BLUE_VIOLET);
			g.drawRect(skin.getRectangle());
			
		}

	}
	
}
