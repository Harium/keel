package br.com.etyllica.examples;

import java.awt.Color;

import br.com.etyllica.camera.Camera;
import br.com.etyllica.camera.CameraV4L4J;
import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.BufferedLayer;
import br.com.etyllica.linear.Point2D;
import br.com.etyllica.motion.core.ColorFilter;
import br.com.etyllica.motion.features.Component;

public class SimpleCam extends Application{

	public SimpleCam(int w, int h) {
		super(w, h);
	}

	private Camera cam;

	private ColorFilter colorFilter;

	private BufferedLayer mirror;

	private Component screen = new Component(w, h);

	private Point2D point;

	@Override
	public void load() {

		loadingPhrase = "Opening Camera";

		cam = new CameraV4L4J(0);

		loadingPhrase = "Setting Filter";

		colorFilter = new ColorFilter();
		colorFilter.setBorder(20);
		colorFilter.setTolerance(20);

		mirror = new BufferedLayer(0, 0);

		loading = 100;
	}

	@Override
	public void update(long now){

		//Get the Camera image
		mirror.setBuffer(cam.getBufferedImage());

		//Normally the camera shows the image flipped, but we want to see something like a mirror
		//So we flip the image
		mirror.flipHorizontal();

		//Now we search for the first pixel with the desired color in the whole screen
		point = colorFilter.filterFirst(mirror.getModifiedBuffer(), screen);

	}

	@Override
	public void draw(Graphic g) {

		//Draw the mirror image
		mirror.draw(g);

		//Set a Color to our Point
		g.setColor(Color.CYAN);

		//Draw our tracking point with radius = 10 pixels
		g.fillCircle((int)point.getX(), (int)point.getY(), 10);

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)){
			//When mouse clicks with LeftButton, the color filter tries to find
			//the color we are clicking on
			colorFilter.setColor(mirror.getModifiedBuffer().getRGB((int)event.getX(), (int)event.getY()));
		}

		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

}
