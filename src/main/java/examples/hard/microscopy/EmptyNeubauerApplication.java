package examples.hard.microscopy;

import java.awt.Color;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.MouseEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;

public class EmptyNeubauerApplication extends Application {

	protected int zoom = 4;
	
	private int dragX = 0;
	private int dragY = 0;

	private boolean mouseDrag = false;

	protected int offsetX = 0;
	protected int offsetY = 0;

	private Color background = new Color(0xC3, 0xB3, 0xA4);

	public EmptyNeubauerApplication(int w, int h) {
		super(w, h);
	}
	
	private int spacing005mm = 0;

	protected int lineSize = 0;

	@Override
	public void load() {
		
		changeZoom();
		
		loading = 100;
	}
	
	private void changeZoom() {
		
		spacing005mm = 4*zoom;
		
		lineSize = spacing005mm*60;
		
	}

	@Override
	public void draw(Graphics g) {
		
		drawNeubauer(g);
		
		drawInformation(g);

	}
	
	protected void drawNeubauer(Graphics g){
		
		g.setColor(background);
		g.fillRect(0, 0, w, h);

		g.setLineWidth(1f);

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
	
	protected void drawInformation(Graphics g) {
		
		g.setColor(Color.WHITE);
		
		g.setFontSize(20f);		
		
		g.drawStringShadow("Zoom: "+zoom+"x", 20, 60);
		
	}

	private void drawStrongLines(Graphics g, int spacing005mm) {
		
		g.setLineWidth(1f);
		
		for(int i=0;i<4; i++) {
			
			drawVerticalStrongLine(g, spacing005mm, i*4*5);			
			
			drawHorizontalLine(g, spacing005mm, i*4*5);
			
		}
		
		
		
	}
	
	private void drawMiddleLines(Graphics g, int spacing005mm) {

		//Strong Lines
		for(int i=0;i<=5;i++) {
			
			drawVerticalStrongLine(g, spacing005mm, 4*5+4*i);
			drawHorizontalStrongLine(g, spacing005mm, 4*5+4*i);
			
		}
		
		//Thin lines
		for(int i=0;i<20;i++) {

			drawVerticalLine(g, spacing005mm, 4*5+i);
			drawHorizontalLine(g, spacing005mm, 4*5+i);

		}


	}
	
	private void drawHorizontalStrongLine(Graphics g, int spacing, int i) {

		drawHorizontalLine(g, 0, spacing, i);
		drawHorizontalLine(g, -1*zoom/4, spacing, i);
		drawHorizontalLine(g, +1*zoom/4, spacing, i);

	}
	
	private void drawHorizontalLine(Graphics g, int spacing, int i) {
		drawHorizontalLine(g, 0, spacing, i);
	}

	private void drawHorizontalLine(Graphics g, int offset, int spacing, int i) {

		g.drawLine(offsetX, offsetY+offset+(spacing)*i, offsetX+lineSize, offsetY+offset+(spacing)*i);

	}

	private void drawVerticalLine(Graphics g, int spacing, int i) {

		drawVerticalLine(g, 0, spacing, i);

	}
	
	private void drawVerticalStrongLine(Graphics g, int spacing, int i) {

		drawVerticalLine(g, 0, spacing, i);
		drawVerticalLine(g, -1*zoom/4, spacing, i);
		drawVerticalLine(g, +1*zoom/4, spacing, i);

	}

	private void drawVerticalLine(Graphics g, int offset, int spacing, int i) {

		g.drawLine(offsetX+offset+(spacing)*i, offsetY, offsetX+offset+(spacing)*i, offsetY+lineSize);

	}

	@Override
	public void updateMouse(PointerEvent event) {

		if(event.isButtonDown(MouseEvent.MOUSE_WHEEL_UP)) {
			zoom*=2;
			changeZoom();			
		}
		
		if(event.isButtonDown(MouseEvent.MOUSE_WHEEL_DOWN)) {
			
			if(zoom>1) {
				zoom/=2;
				changeZoom();
			}
		
		}		
		
		if(event.isButtonDown(MouseEvent.MOUSE_BUTTON_LEFT)) {

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

		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)) {
			mouseDrag = false;
		}
	}
	
}
