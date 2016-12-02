package application.examples;

import java.awt.Color;
import java.awt.image.BufferedImage;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.MouseButton;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.motion.camera.Camera;
import br.com.etyllica.motion.camera.CameraV4L4J;
import br.com.etyllica.motion.core.source.BufferedImageSource;
import br.com.etyllica.motion.feature.Component;
import br.com.etyllica.motion.filter.color.ColorStrategy;
import br.com.etyllica.motion.filter.search.TriangularSearch;

public class SimpleCam extends Application {

	public SimpleCam(int w, int h) {
		super(w, h);
	}

	private Camera cam;
	private BufferedImageSource source = new BufferedImageSource();
	
	private TriangularSearch colorFilter;

	private ColorStrategy colorStrategy;
	
	private BufferedLayer mirror;

	private Component screen;

	private Component point;

	@Override
	public void load() {
		
		loadingInfo = "Opening Camera";

		cam = new CameraV4L4J(0);
		
		BufferedImage buffer = cam.getBufferedImage();
		
		int w = buffer.getWidth();
		int h = buffer.getHeight();
		
		screen = new Component(0, 0, w, h);

		loadingInfo = "Setting Filter";

		colorFilter = new TriangularSearch(w, h);
		colorFilter.setBorder(20);
		
		colorStrategy = new ColorStrategy(Color.BLACK.getRGB()); 
		colorFilter.setPixelStrategy(colorStrategy);

		mirror = new BufferedLayer(0, 0);

		loading = 100;
	}

	@Override
	public void update(long now) {

		//Get the Camera image
		mirror.setBuffer(cam.getBufferedImage());
		
		//Normally the camera shows the image flipped, but we want to see something like a mirror
		//So we flip the image
		mirror.flipHorizontal();
		source.setImage(mirror.getBuffer());

		//Now we search for the first pixel with the desired color in the whole screen
		point = colorFilter.filterFirst(source, screen);
	}

	@Override
	public void draw(Graphics g) {

		//Draw the mirror image
		mirror.draw(g);

		//Set a Color to our Point
		g.setColor(Color.CYAN);

		//Draw our tracking point with radius = 10 pixels
		g.fillCircle(point.getX(), point.getY(), 10);

	}

	@Override
	public void updateMouse(PointerEvent event) {

		if(event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)){
			//When mouse clicks with LeftButton, the color filter tries to find
			//the color we are clicking on
			colorStrategy.setColor(mirror.getBuffer().getRGB((int)event.getX(), (int)event.getY()));						
		}

	}

}
