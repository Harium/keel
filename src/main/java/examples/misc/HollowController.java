package examples.misc;

import br.com.etyllica.keel.awt.camera.CameraV4L4J;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.ColorPointFilter;
import br.com.etyllica.keel.filter.RedLedFilter;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.context.UpdateIntervalListener;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.core.graphics.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class HollowController extends Application implements UpdateIntervalListener {

    private Component screen;

    public HollowController(int w, int h) {
        super(w, h);
    }

    private CameraV4L4J cam;

    private BufferedImage buf;

    private BufferedImageSource source = new BufferedImageSource();

    private RedLedFilter ledFilter;

    private ColorPointFilter activeFilter;

    private List<Component> lastButtons;

    private List<Component> components;

    @Override
    public void load() {

        cam = new CameraV4L4J(0);
        source.setImage(cam.getBufferedImage());

        final int w = cam.getBufferedImage().getWidth();
        final int h = cam.getBufferedImage().getHeight();

        screen = new Component(w, h);

        //Loading Filters
        ledFilter = new RedLedFilter(w, h);
        activeFilter = new ColorPointFilter(w, h, Color.WHITE);

        lastButtons = new ArrayList<Component>(8);

        loading = 100;

    }

    public void timeUpdate(long now) {
        System.out.println("TIME UPDATE");
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        if (event.isKeyDown(KeyEvent.VK_R)) {
            activated = false;
        }
    }

    private boolean activated = false;

    @Override
    public void draw(Graphics g) {

        buf = cam.getBufferedImage();
        source.setImage(buf);

        g.drawImage(buf, 0, 0);

        activated = false;

        for (Component component : lastButtons) {

            List<Component> active = activeFilter.filter(source, component);

            Color color = Color.YELLOW;

            if (active != null) {

                color = Color.RED;
                activated = true;

            }
            g.setColor(color);

            g.drawRect(component.getLayer());

        }

        if (!activated) {

            components = ledFilter.filter(source, screen);

            if (components != null) {

                Color color = Color.GREEN;
                if (components.size() == 8) {

                    lastButtons.clear();
                    lastButtons.addAll(components);

                    color = Color.BLUE;
                }

                for (Component component : components) {
                    g.setColor(color);
                    g.drawRect(component.getLayer());
                    /*g.setColor(Color.WHITE);
					g.escreveLabelSombra(component.getMenorX(), component.getMenorY(), component.getW(), component.getH(), Integer.toString(component.getNumeroPontos()),Color.BLACK);*/
                }

            }
        }

    }

}
