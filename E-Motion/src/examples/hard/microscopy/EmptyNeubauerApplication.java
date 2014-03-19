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

	//if 1mm = 128px
	
	final int spacing1mm = 64;
	
	final int spacing025mm = spacing1mm/4;
	
	final int spacing005mm = spacing025mm/4;
	
	final int lineSize = 1100;
	
	final float lineWidth = 8f;
	
	@Override
	public void draw(Graphic g) {
				
		g.setColor(background);
		g.fillRect(0, 0, w, h);

		g.setColor(Color.WHITE);
		g.setBasicStroke(1);
				
		//Draw 1mm lines
		for(int i=1;i<13;i++) {
			
			drawHorizontalLine(g, spacing1mm, i);
			
			drawVerticalLine(g, spacing1mm, i);
			
		}
		
		g.setColor(Color.WHITE);
		//Draw 0.25mm lines
		for(int i=1;i<13;i++) {
			
			drawHorizontalLine(g, spacing025mm+spacing1mm*4, spacing025mm, i);
			
			drawVerticalLine(g, spacing025mm+spacing1mm*4 , spacing025mm, i);
			
		}
		
	}
	
	public void drawHorizontalLine(Graphic g, int spacing, int i) {
		drawHorizontalLine(g, 0, spacing, i);
	}
	
	public void drawHorizontalLine(Graphic g, int offset, int spacing, int i) {
		
		float lineOffset = lineWidth+spacing;
		
		g.drawLine(offsetX, offsetY+offset+(lineOffset)*i, offsetX+lineSize, offsetY+offset+(lineOffset)*i);
		
	}
	
	public void drawVerticalLine(Graphic g, int spacing, int i) {
		
		drawVerticalLine(g, 0, spacing, i);
		
	}
	
	public void drawVerticalLine(Graphic g, int offset, int spacing, int i) {
		
		float lineOffset = lineWidth+spacing;
		
		g.drawLine(offsetX+offset+(lineOffset)*i, offsetY, offsetX+offset+(lineOffset)*i, offsetY+lineSize);
		
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
