package examples.medium.skin;

import br.com.etyllica.keel.awt.camera.Camera;
import br.com.etyllica.keel.awt.camera.FakeCamera;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.core.helper.ColorHelper;
import br.com.etyllica.keel.core.strategy.SearchFilter;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.SkinColorFilter;
import br.com.etyllica.keel.filter.color.skin.SkinColorKovacNewStrategy;
import br.com.etyllica.keel.filter.validation.MinDimensionValidation;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.event.KeyEvent;
import com.harium.etyl.commons.event.MouseEvent;
import com.harium.etyl.commons.event.PointerEvent;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.linear.Point2D;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class SimpleSkinStrategyApplication extends Application {

    protected Camera cam = new FakeCamera();
    private BufferedImageSource source = new BufferedImageSource();

    private final int IMAGES_TO_LOAD = 90;

    private SkinColorFilter skinFilter;

    private List<Component> skinComponents;

    private Component screen;

    private Color color = Color.BLACK;

    private boolean drawPoints = false;
    private boolean leftPoints = true;

    public SimpleSkinStrategyApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {
        loading = 0;

        loadingInfo = "Loading Images";

        initCamera();

        loadingInfo = "Configuring Filter";

        loading = 25;
        reset();

        loading = 50;
    }

    protected void initCamera() {
        for (int i = 1; i <= IMAGES_TO_LOAD; i++) {
            loading = i;

            ((FakeCamera) cam).addImage("skin/skin" + Integer.toString(i) + ".jpg");
        }
    }

    @Override
    public void updateKeyboard(KeyEvent event) {
        if (event.isKeyDown(KeyEvent.VK_RIGHT)) {
            ((FakeCamera) cam).nextFrame();
            reset();
        } else if (event.isKeyDown(KeyEvent.VK_LEFT)) {
            ((FakeCamera) cam).previousFrame();
            reset();
        } else if (event.isKeyDown(KeyEvent.VK_SPACE)) {
            drawPoints = !drawPoints;
        }

        if (event.isKeyDown(KeyEvent.VK_1)) {
            leftPoints = true;
        }

        if (event.isKeyDown(KeyEvent.VK_2)) {
            leftPoints = false;
        }
    }

    @Override
    public void updateMouse(PointerEvent event) {

        if (event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)) {
            int x = event.getX();
            int y = event.getY();

            BufferedImage buffer = cam.getBufferedImage();

            if (x < buffer.getWidth() && y < buffer.getHeight()) {

                int rgb = buffer.getRGB(x, y);
                final int R = ColorHelper.getRed(rgb);
                final int G = ColorHelper.getGreen(rgb);
                final int B = ColorHelper.getBlue(rgb);

                System.out.println(R + " " + G + " " + B + " RG_MOD=" + (R - G) + " R-B=" + (R - B));
            }
        }
    }

    protected void reset() {
        //Define the area to search for elements
        int w = cam.getBufferedImage().getWidth();
        int h = cam.getBufferedImage().getHeight();

        screen = new Component(0, 0, w, h);
        skinFilter = new SkinColorFilter(w, h, new SkinColorKovacNewStrategy());

        SearchFilter filter = skinFilter.getSearchStrategy();
        filter.setStep(2);
        filter.setBorder(4);

        //Remove components smaller than 20x20
        skinFilter.addValidation(new MinDimensionValidation(20));

        source.setImage(cam.getBufferedImage());
        skinComponents = skinFilter.filter(source, screen);

        color = randomColor();
    }

    private Color randomColor() {
        int r = new Random().nextInt(255);
        int g = new Random().nextInt(255);
        int b = new Random().nextInt(255);

        return new Color(r, g, b);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(cam.getBufferedImage(), 0, 0);

        //Draw a red line around the components
        drawComponents(g);
    }

    protected void drawComponents(Graphics g) {
        for (int i = 0; i < skinComponents.size(); i++) {
            Component component = skinComponents.get(i);

            //g.setStroke(new BasicStroke(3f));

            g.setColor(color);
            g.drawRect(component.getRectangle());

            g.setColor(Color.BLACK);
            g.drawString(Double.toString(component.getDensity()), component.getRectangle());

            if (drawPoints) {
                for (Point2D point : component.getPoints()) {

                    if (leftPoints) {
                        if (point.getX() < w / 2) {
                            g.fillRect((int) point.getX(), (int) point.getY(), 1, 1);
                        }
                    } else if (point.getX() >= w / 2) {
                        g.fillRect((int) point.getX(), (int) point.getY(), 1, 1);
                    }
                }
            }
        }
    }
}
