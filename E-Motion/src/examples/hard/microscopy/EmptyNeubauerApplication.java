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
	
	private int zoom = 1;

	private int spacing005mm = 16*zoom;
		
	private int lineSize = spacing005mm*60;

	@Override
	public void load() {
		
		changeZoom();
		
		loading = 100;
	}
	
	private void changeZoom() {
		
		spacing005mm = 16*zoom;
		
		lineSize = spacing005mm*60;
		
	}

	@Override
	public void draw(Graphic g) {
		
		g.setColor(background);
		g.fillRect(0, 0, w, h);

		g.setBasicStroke(1);

		g.setColor(Color.WHITE);
		
		//Draw 0.05mm lines
		for(int i=0;i<=60;i++) {

			//Draw spacing 025
			if(i%5==0) {
				drawHorizontalLine(g, spacing005mm, i);
				drawVerticalLine(g, spacing005mm, i);
			}

		}

		drawStrongLines(g,spacing005mm);

		drawMiddleLines(g, spacing005mm);

	}

	private void drawStrongLines(Graphic g, int spacing005mm) {
		
		g.setBasicStroke(3f);
		
		for(int i=0;i<4; i++) {
			drawVerticalLine(g, spacing005mm, i*4*5);
			drawHorizontalLine(g, spacing005mm, i*4*5);
		}
		
	}
	
	private void drawMiddleLines(Graphic g, int spacing005mm) {

		//Strong Lines
		g.setBasicStroke(3f);
		
		for(int i=0;i<=5;i++) {
			
			drawVerticalLine(g, spacing005mm, 4*5+4*i);
			drawHorizontalLine(g, spacing005mm, 4*5+4*i);
			
		}
		
		//Thin lines
		g.setBasicStroke(1f);

		for(int i=0;i<20;i++) {

			drawVerticalLine(g, spacing005mm, 4*5+i);
			drawHorizontalLine(g, spacing005mm, 4*5+i);

		}


	}

	private void drawHorizontalLine(Graphic g, int spacing, int i) {
		drawHorizontalLine(g, 0, spacing, i);
	}

	private void drawHorizontalLine(Graphic g, int offset, int spacing, int i) {

		g.drawLine(offsetX, offsetY+offset+(spacing)*i, offsetX+lineSize, offsetY+offset+(spacing)*i);

	}

	private void drawVerticalLine(Graphic g, int spacing, int i) {

		drawVerticalLine(g, 0, spacing, i);

	}

	private void drawVerticalLine(Graphic g, int offset, int spacing, int i) {

		g.drawLine(offsetX+offset+(spacing)*i, offsetY, offsetX+offset+(spacing)*i, offsetY+lineSize);

	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		if(event.onButtonDown(MouseButton.MOUSE_WHEEL_UP)) {
			zoom*=2;
			changeZoom();			
		}
		
		if(event.onButtonDown(MouseButton.MOUSE_WHEEL_DOWN)) {
			
			if(zoom>1) {
				zoom/=2;
				changeZoom();
			}
		
		}		
		
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
