package examples.misc;

import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.event.PointerEvent;
import com.harium.etyl.core.graphics.Graphics;

import java.awt.*;

public class LuviaMotion extends Application {

    int kin = 0;

    public LuviaMotion(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {

        loading = 100;
    }

    private float mx = 0;
    private float my = 0;

    @Override
    public void updateMouse(PointerEvent event) {

        mx = event.getX();
        my = event.getY();
    }

    @Override
    public void draw(Graphics g) {
        g.setAlpha(100);
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);

        g.setColor(Color.BLACK);
        g.setAlpha(50);
        g.fillCircle(100, 100, 80);
        g.setAlpha(100);

        g.getGraphics().fillOval(500, 300, 10, 10);

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

    private void drawCircle(Graphics g) {

        int cw = 100;
        int ch = 100;

        float cx = mx - cw / 2;
        float cy = my - ch / 2;

        int linew = 3;

        g.setAlpha(50);

        //g.setColor(new Color(0xff,0xff,0xff));
        g.setColor(new Color(0x0, 0x0, 0x0));

        //Draw outer line
        g.drawArc(cx, cy, cw, ch, 0, 360);

        int offset = 2;

        //Loop para draw inner lines
        for (int i = 0; i < linew; i++) {

            g.drawArc((cx + (offset / 2)), cy + (offset / 2), cw - offset, ch - offset, 0, 360);

            offset *= 2;

        }

    }

    private void drawContent(Graphics g, int percent) {
        int angle = percent;

        int cw = 100;
        int ch = 100;

        float cx = mx - cw / 2;
        float cy = my - ch / 2;

        g.setAlpha(100);

        g.setColor(new Color(0, 0, 0xff));
        g.drawArc(cx, cy, 100, 100, 0, angle);

        g.setColor(new Color(0, 0, 0x99));
        g.drawArc(cx + ((100 - 98) / 2), cy + ((100 - 98) / 2), 98, 98, 0, angle);

        g.setColor(new Color(0, 0, 0x66));
        g.drawArc(cx + ((100 - 96) / 2), cy + ((100 - 96) / 2), 96, 96, 0, angle);
    }

}
