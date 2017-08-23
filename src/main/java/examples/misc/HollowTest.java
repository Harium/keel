package examples.misc;

import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.RedLedFilter;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.loader.image.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class HollowTest extends Application {

    private Component screen;
    private RedLedFilter filter;
    private BufferedImageSource source = new BufferedImageSource();

    private BufferedImage test;

    public HollowTest(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {

        //TODO Change to Camera Size in Real Application
        filter = new RedLedFilter(w, h);

        screen = new Component(w, h);

        BufferedImage test1 = ImageLoader.getInstance().getImage("test/test1.png");
        BufferedImage test2 = ImageLoader.getInstance().getImage("test/test2.png");
        BufferedImage test3 = ImageLoader.getInstance().getImage("test/test3.png");
        BufferedImage test7 = ImageLoader.getInstance().getImage("test/test7.png");
        BufferedImage test8 = ImageLoader.getInstance().getImage("test/test8.png");

        test = test7;

        loading = 100;
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(test, 0, 0);

        source.setImage(test);

        List<Component> components = filter.filter(source, screen);

        Color color = Color.RED;
        if (components.size() == 8) {
            color = Color.BLUE;
        }

        for (Component component : components) {
            g.setColor(color);
            g.drawRect(component.getLayer());
            g.setColor(Color.WHITE);
            g.drawStringShadow(Integer.toString(component.getPointCount()), component.getLowestX(), component.getLowestY(), component.getW(), component.getH(), Color.BLACK);
        }

    }

}
