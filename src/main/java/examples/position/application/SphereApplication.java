package examples.position.application;


import br.com.etyllica.keel.awt.camera.Camera;
import br.com.etyllica.keel.awt.camera.CameraSarxosWebcam;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.classifier.CircleClassifier;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.ColorFilter;
import br.com.etyllica.keel.filter.validation.MaxDimensionValidation;
import br.com.etyllica.keel.filter.validation.MinDimensionValidation;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.commons.event.MouseEvent;
import com.harium.etyl.commons.event.PointerEvent;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.layer.BufferedLayer;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SphereApplication extends Application {

    Color color = Color.BLUE;
    ColorFilter colorFilter;

    Component screen;
    BufferedImageSource source;
    private BufferedLayer layer;

    List<Component> components;
    Set<Component> circle = new HashSet<>();

    private Camera cam;

    private boolean hide = false;

    private int xOffset = 0;
    private int yOffset = 0;

    public SphereApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {
        source = new BufferedImageSource();
        screen = setupCamera();

        colorFilter = new ColorFilter(w, h, color);
        colorFilter.setTolerance(30);
        colorFilter.addValidation(new MinDimensionValidation(50));
        colorFilter.addValidation(new MaxDimensionValidation(300));
    }

    protected Component setupCamera() {
        cam = new CameraSarxosWebcam(0);

        int w = cam.getBufferedImage().getWidth();
        int h = cam.getBufferedImage().getHeight();

        screen = new Component(0, 0, w, h);
        layer = new BufferedLayer(w, h);

        return screen;
    }

    public void update(long now) {
        updateFrame(cam.getBufferedImage());
    }

    private void updateFrame(BufferedImage bufferedImage) {
        layer.setBuffer(bufferedImage);
        layer.flipHorizontal();

        source.setImage(layer.getBuffer());

        components = colorFilter.filter(source, screen);

        circle.clear();
        CircleClassifier classifier = new CircleClassifier();
        for (Component component : components) {
            if (classifier.classify(component)) {
                circle.add(component);
            }
        }
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        if (event.isKeyDown(KeyEvent.VK_H)) {
            hide = !hide;
        }
    }

    private Color pickColor(int px, int py) {
        return new Color(layer.getBuffer().getRGB(px, py));
    }

    @Override
    public void updateMouse(PointerEvent event) {

        if (event.isButtonDown(MouseEvent.MOUSE_BUTTON_LEFT)) {
            color = pickColor(event.getX(), event.getY());
            colorFilter.setColor(color);

            System.out.println(color.getRed());
            System.out.println(color.getGreen());
            System.out.println(color.getBlue());
            System.out.println("---------");
        }
    }

    /*private void drawScreen(BufferedImage image) {
        Graphics g = new AWTGraphics(image);
        g.setColor(Color.BLACK);
        g.fillRect(this);

        g.setColor(color);
        g.fillCircle(120, 200, 80);
        g.fillCircle(300, 200, 60);

        g.fillRect(30, 400, 260, 20);
    }*/

    @Override
    public void draw(Graphics g) {
        if (!hide) {
            g.drawImage(layer.getBuffer(), xOffset, yOffset);
        }

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
