package examples.hard.microscopy;

import java.awt.Color;

import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;

public class EmptyNeubauerApplication extends Application {

	private int dragX = 0;
	private int dragY = 0;
	
	private boolean mouseDrag = false;

	private int offsetX = 0;
	private int offsetY = 0;
	
	private Color background = new Color(0xC3, 0xB3, 0xA4);

	public EmptyNeubauerApplication(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		loading = 100;
	}

	@Override
	public void draw(Graphic g) {

		g.setColor(background);
		g.fillRect(0, 0, w, h);

		g.setColor(Color.WHITE);
		g.setBasicStroke(8f);
		
		
		//Draw Vertical Lines
		int lineX = 10;
		
		for(int i=0;i<3;i++) {
			
			final int lineOffset = 18;
		
			g.drawLine(offsetX+lineX+lineOffset*i, offsetY, offsetX+lineX+lineOffset*i, offsetY+2000);
			
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
