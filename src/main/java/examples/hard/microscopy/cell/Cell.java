package examples.hard.microscopy.cell;

import java.awt.Color;

import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.layer.Layer;

public class Cell extends Layer {
		
	private Color borderColor = new Color(0x7C, 0x66, 0x59);
	
	protected final float radius = 0.7f;
	
	protected final float borderWidth = 0.2f;
	
	protected Color color = Color.WHITE;
	
	public Cell(int x, int y) {
		super(x, y);
		
		this.opacity = 170;
		
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, int zoom) {
		
		int cx = offsetX+x*zoom;
		
		int cy = offsetY+y*zoom;
		
		//Draw Citoplasm
		g.setColor(color);
		g.setOpacity(opacity);
		g.fillCircle(cx, cy, (int)((radius-borderWidth/2)*zoom));
		
		//Draw Nucleus
		g.setColor(borderColor);
		g.fillCircle(cx, cy, (int)(radius/2*zoom));
		
		//Draw Border
		g.setLineWidth(borderWidth*zoom);
		g.drawCircle(cx, cy, (int)(radius*zoom));
		
		g.setOpacity(255);
		
	}
	
}
