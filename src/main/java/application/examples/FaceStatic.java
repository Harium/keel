package application.examples;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.List;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.keel.awt.camera.FakeCamera;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.color.ColorStrategy;
import br.com.etyllica.keel.filter.color.skin.SkinColorStrategy;
import br.com.etyllica.keel.filter.search.CrossSearch;
import br.com.etyllica.keel.filter.search.TriangularSearch;
import br.com.etyllica.keel.modifier.EnvelopeModifier;
import br.com.etyllica.keel.modifier.hull.FastConvexHullModifier;

public class FaceStatic extends Application {

	private FakeCamera cam = new FakeCamera();
	private BufferedImageSource source = new BufferedImageSource();

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
	
	private FastConvexHullModifier modifier;

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
		
		modifier = new FastConvexHullModifier();
		
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

		source.setImage(b);
		
		//Sampled
		blackSampledFeature = blackFilter.filterFirst(source, screen);
		blackPolygon.reset();

		blackPolygon = blackSampledFeature.getPolygon();
		
		
		//White Component
		lightDirection = whiteFilter.filterFirst(source, screen);
		whitePolygon.reset();
		
		whitePolygon = lightDirection.getPolygon();
		
		skinFeatures = skinFilter.filter(source, screen);
				
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

		if(event.isKeyDown(KeyEvent.VK_C)){
			drawCleanedOnly = !drawCleanedOnly;
		}
		
		if(event.isKeyDown(KeyEvent.VK_B)){
			drawBox = !drawBox;
		}
	}

	@Override
	public void draw(Graphics g) {
		
		if(!hide){
			g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
		}

		g.setColor(Color.BLUE);

		g.setColor(SVGColor.BLACK);
		g.setLineWidth(2);
		g.drawPolygon(blackPolygon);
		
		g.drawRect(blackSampledFeature.getRectangle());
		
		g.setColor(SVGColor.WHITE);
		g.setLineWidth(1);
		
		g.setColor(SVGColor.ORANGE);
		g.drawPolygon(whitePolygon);
		
		g.drawRect(lightDirection.getRectangle());
		
		g.setLineWidth(2);
		for(Component skin: skinFeatures){
			
			g.setColor(SVGColor.BLUE_VIOLET);
			g.drawRect(skin.getRectangle());
			
		}

	}
	
}
