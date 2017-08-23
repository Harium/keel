package examples.medium.application;

import br.com.etyllica.keel.awt.camera.FakeCamera;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.ColorFilter;
import br.com.etyllica.keel.filter.validation.MinDensityValidation;
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

public class TrackingShadingColorApplication extends Application {

    private FakeCamera cam;
    private BufferedImageSource source = new BufferedImageSource();

    private ColorFilter blueFilter;

    //Blue Marker
    private Color darkColor = new Color(34, 40, 52);
    private Color color = new Color(54, 71, 79);

    private int tolerance = 10;
    private int minDensity = 12;
    private int minDimension = 10;

    private MinDensityValidation densityValidation;
    private MinDimensionValidation dimensionValidation;

    private boolean hide = false;
    private boolean markers = true;
    private boolean pixels = true;

    private int xOffset = 0;
    private int yOffset = 0;

    private Component screen;

    private List<Component> blueComponents;

    public TrackingShadingColorApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {

        loadingInfo = "Loading Images";

        screen = setupCamera();

        densityValidation = new MinDensityValidation(minDensity);
        dimensionValidation = new MinDimensionValidation(minDimension);

        blueFilter = new ColorFilter(screen.getW(), screen.getH(), color, tolerance);
        blueFilter.getSearchStrategy().addValidation(dimensionValidation);
        blueFilter.getSearchStrategy().addValidation(densityValidation);

        final int MAGIC_NUMBER = 3;//Higher = Faster and less precise

        blueFilter.getSearchStrategy().setBorder(MAGIC_NUMBER);
        blueFilter.getSearchStrategy().setStep(2);

        loadingInfo = "Configuring Filter";

        loading = 60;
        reset(cam.getBufferedImage());

        loading = 100;
    }

    protected Component setupCamera() {
        cam = new FakeCamera();

        for (int i = 1; i <= 3; i++) {
            cam.addImage("dumbbells/dumbbells" + Integer.toString(i) + ".png");
        }

        int w = cam.getBufferedImage().getWidth();
        int h = cam.getBufferedImage().getHeight();

        screen = new Component(0, 0, w, h);

        return screen;
    }

    int bx = 0;
    int by = 0;
    int bRadius = 0;

    private void reset(BufferedImage b) {
        source.setImage(b);

        blueComponents = blueFilter.filter(source, screen);

        if (!blueComponents.isEmpty()) {

            bx = 0;
            by = 0;

            bRadius = 0;

            for (Component component : blueComponents) {
                Point2D p = component.getCenter();
                bx += p.getX();
                by += p.getY();

                bRadius += (component.getW() + component.getH()) / 4;
            }

            bx /= blueComponents.size();
            by /= blueComponents.size();

            bRadius /= blueComponents.size();

            return;
        }
    }

    @Override
    public void updateMouse(PointerEvent event) {

        if (event.isButtonDown(MouseEvent.MOUSE_BUTTON_LEFT)) {
            color = pickColor(event.getX(), event.getY());
            blueFilter.setColor(color);

            System.out.println(color.getRed());
            System.out.println(color.getGreen());
            System.out.println(color.getBlue());
            System.out.println("---------");
        }
    }

    private Color pickColor(int px, int py) {
        return new Color(cam.getBufferedImage().getRGB(px, py));
    }

    @Override
    public void updateKeyboard(KeyEvent event) {

        if (event.isKeyDown(KeyEvent.VK_H)) {
            hide = !hide;
        }

        if (event.isKeyDown(KeyEvent.VK_P)) {
            markers = !markers;
        }

        //Change Tolerance
        if (event.isKeyUp(KeyEvent.VK_EQUALS)) {
            tolerance++;
            blueFilter.setTolerance(tolerance);
        } else if (event.isKeyUp(KeyEvent.VK_MINUS)) {
            tolerance--;
            blueFilter.setTolerance(tolerance);
        }

        //Change Density
        if (event.isKeyUp(KeyEvent.VK_P)) {
            minDensity++;
            densityValidation.setDensity(minDensity);
        } else if (event.isKeyUp(KeyEvent.VK_O)) {
            minDensity--;
            densityValidation.setDensity(minDensity);
        }

        //Change Dimension
        if (event.isKeyUp(KeyEvent.VK_L)) {
            minDimension++;
            dimensionValidation.setDimension(minDimension);
        } else if (event.isKeyUp(KeyEvent.VK_K)) {
            minDimension--;
            dimensionValidation.setDimension(minDimension);
        }

        if (event.isKeyUp(KeyEvent.VK_RIGHT_ARROW)) {
            cam.nextFrame();
        } else if (event.isKeyUp(KeyEvent.VK_LEFT_ARROW)) {
            cam.previousFrame();
        }
    }

    @Override
    public void draw(Graphics g) {

        if (!hide) {
            g.drawImage(cam.getBufferedImage(), xOffset, yOffset);
        }

        g.setColor(color);
        g.fillRect(0, 0, 60, 80);

        g.setColor(Color.BLACK);

        if (markers) {

            reset(cam.getBufferedImage());

            g.drawString("Tol: " + Integer.toString(tolerance), 10, 80);
            g.drawString("Den: " + Integer.toString(minDensity), 10, 100);
            g.drawString("Dim: " + Integer.toString(minDimension), 10, 120);

            g.setAlpha(60);
            //drawFeaturedPoints(g, sampledFeature, Color.GREEN);
            g.setAlpha(100);

            g.setColor(Color.GREEN);

            if (blueComponents != null) {
                for (Component component : blueComponents) {
                    g.drawPolygon(component.getBoundingBox());
                    g.drawString(Double.toString(component.getDensity()), component.getRectangle());

					/*for(Point2D point: component.getPoints()) {
                        g.fillRect((int)point.getX(), (int)point.getY(), 1, 1);
					}*/
                }
            }

            g.setAlpha(50);
            g.fillCircle(bx, by, bRadius);
            g.resetOpacity();

        }
    }
}