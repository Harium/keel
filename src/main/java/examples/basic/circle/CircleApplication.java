package examples.basic.circle;


import br.com.etyllica.awt.AWTGraphics;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.classifier.CircleClassifier;
import br.com.etyllica.keel.core.source.ImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.ColorFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CircleApplication extends Application {

    Color color = Color.BLUE;
    ColorFilter colorFilter;

    BufferedImage drawing;

    List<Component> components;
    Set<Component> circle = new HashSet<>();

    public CircleApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {
        drawing = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        drawScreen(drawing);

        ImageSource source = new BufferedImageSource(drawing);
        Component screen = new Component(w, h);

        colorFilter = new ColorFilter(w, h, color);
        components = colorFilter.filter(source, screen);

        CircleClassifier classifier = new CircleClassifier();

        for (Component component : components) {
            if (classifier.classify(component)) {
                circle.add(component);
            }
        }
    }

    private void drawScreen(BufferedImage image) {
        Graphics g = new AWTGraphics(image);
        g.setColor(Color.BLACK);
        g.fillRect(this);

        g.setColor(color);
        g.fillCircle(120, 200, 80);
        g.fillCircle(300, 200, 60);

        g.fillRect(30, 400, 260, 20);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(drawing, 0, 0);

        for (Component component : components) {
            if (!circle.contains(component)) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }
            g.drawRect(component.getRectangle());
        }
    }
}
