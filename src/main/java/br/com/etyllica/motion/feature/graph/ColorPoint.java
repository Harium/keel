package br.com.etyllica.motion.feature.graph;

import java.awt.Color;

import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.Layer;

public class ColorPoint extends Layer {
	
	private int r,g,b; 
	
	private boolean over = false;
	
	private Color color;
	
	public ColorPoint(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
		
		this.x = r-2;
		this.y = (b+(g-b));
		
		if(b>g){
			color = Color.BLUE;
			//this.y+=256;
		}else{
			color = Color.GREEN;
		}
		
		this.y -=2;
		
		this.w = 4;
		this.h = 4;		
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
		
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void draw(Graphic g){
		
		g.setColor(color);
		
		if(over){
			g.fillCircle(x+2, y+2, 8);
		}
		else{
			g.fillCircle(x+2, y+2, 4);
		}
	}

	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}
		
}
