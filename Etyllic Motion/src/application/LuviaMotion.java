package application;

import java.awt.Color;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyboardEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Grafico;

public class LuviaMotion extends Application{

	int kin = 0;

	public LuviaMotion(int w, int h){
		super(w,h);
	}

	@Override
	public void load() {

		loading = 100;
	}
	
	private int mx = 0;
	private int my = 0;
	
	@Override
	public GUIEvent updateMouse(PointerEvent event){
		
		mx = event.getX();
		my = event.getY();
		
		return GUIEvent	.NONE;
	}
	
	@Override
	public void draw(Grafico g){
		g.setAlpha(100);
		g.setColor(Color.white);
		g.fillRect(0, 0, w, h);

		g.setColor(Color.BLACK);
		g.setAlpha(50);
		g.fillCircle(100,100,80);
		g.setAlpha(100);
		
		g.getGraphics().fillOval(500,300,10,10);
		
		drawCircle(g);
		
		/*if(mouse.sobMouseCircular(100, 100, 80)){
			kin+=2;
			kin%=360;
			drawContent(g, kin);
		}
		else if(mouse.sobMouseCircular(500+5, 300+5, 5)){
			kin+=2;
			kin%=360;
			drawContent(g, kin);
		}else{
			kin = 0;
		}*/


	}

	private void drawCircle(Grafico g){

		int cw = 100;
		int ch = 100;

		int cx = mx-cw/2;
		int cy = my-ch/2;
		
		int linew = 3;

		g.setAlpha(50);

		//g.setColor(new Color(0xff,0xff,0xff));
		g.setColor(new Color(0x0,0x0,0x0));

		//Desenha Linha mais externa
		g.getGraphics().drawArc(cx, cy, cw, ch, 0, 360);

		int offset = 2;
		
		//Loop para desenhar linhas internas
		for(int i = 0;i<linew; i++){

			g.getGraphics().drawArc((cx+(offset/2)), cy+(offset/2), cw-offset, ch-offset, 0, 360);

			offset*=2;

		}

	}

	private void drawContent(Grafico g, int percent){

		int angulo = percent;
		
		int cw = 100;
		int ch = 100;

		int cx = mx-cw/2;
		int cy = my-ch/2;
		
		g.setAlpha(100);

		g.setColor(new Color(0,0,0xff));
		g.getGraphics().drawArc(cx, cy, 100, 100, 0, angulo);

		g.setColor(new Color(0,0,0x99));
		g.getGraphics().drawArc(cx+((100-98)/2), cy+((100-98)/2), 98, 98, 0, angulo);

		g.setColor(new Color(0,0,0x66));
		g.getGraphics().drawArc(cx+((100-96)/2), cy+((100-96)/2), 96, 96, 0, angulo);		
	}

	@Override
	public GUIEvent updateKeyboard(KeyboardEvent arg0) {
		// TODO Auto-generated method stub
		return GUIEvent	.NONE;
	}
	
}
