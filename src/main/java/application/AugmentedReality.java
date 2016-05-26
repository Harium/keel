package application;

import java.awt.Color;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;

public class AugmentedReality extends Application{

	float xAxis = 0;
	float yAxis = 0;
	//float zAxis = 0;
	
	public AugmentedReality(int w, int h) {
		super(w, h);
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
