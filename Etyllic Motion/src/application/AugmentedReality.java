package application;

import java.awt.Color;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Grafico;

public class AugmentedReality extends Application{

	float xAxis = 0;
	float yAxis = 0;
	//float zAxis = 0;
	
	public AugmentedReality(int w, int h) {
		super(w, h);
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateKeyboard(KeyboardEvent event) {
		// TODO Auto-generated method stub
		return GUIEvent.NONE;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		loading = 100;
	}

	@Override
	public void draw(Grafico g) {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		
	}

}
