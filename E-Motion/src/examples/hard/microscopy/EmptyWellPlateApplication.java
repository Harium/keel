package examples.hard.microscopy;

import java.awt.Color;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;

public class EmptyWellPlateApplication extends Application {

	private int dragX = 0;
	private int dragY = 0;
	private boolean mouseDrag = false;

	private int offsetX = 0;
	private int offsetY = 0;
	
	private final int WELL_PLATES = 3;
	
	private final int PLATE_RADIUS = 3000;

	public EmptyWellPlateApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		loading = 100;
	}
	
	@Override
	public void draw(Graphic g) {
		
		for(int i=0; i<WELL_PLATES; i++) {
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(offsetX+PLATE_RADIUS*i, offsetY, PLATE_RADIUS, PLATE_RADIUS);

			g.setColor(Color.BLACK);
			g.setBasicStroke(20f);
			g.drawOval(offsetX+PLATE_RADIUS*i, offsetY, PLATE_RADIUS, PLATE_RADIUS);
			
		}

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			
			if(!mouseDrag) {
				
				dragX = event.getX()-offsetX;
				dragY = event.getY()-offsetY;
				
				mouseDrag = true;
				
			}
		}
		
		if(mouseDrag) {
			offsetX = -(dragX-event.getX());
			offsetY = -(dragY-event.getY());
		}
		
		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {
			mouseDrag = false;
		}

		return null;
	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
