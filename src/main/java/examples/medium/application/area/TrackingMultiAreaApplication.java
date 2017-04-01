package examples.medium.application.area;

import br.com.etyllica.awt.SVGColor;
import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.MouseEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.etyllica.core.linear.Point2D;
import br.com.etyllica.keel.awt.camera.Camera;
import br.com.etyllica.keel.awt.camera.FakeCamera;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.core.helper.ColorHelper;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.ColorFilter;
import br.com.etyllica.keel.filter.search.flood.SoftFloodFillSearch;
import br.com.etyllica.keel.filter.validation.MaxDimensionValidation;
import br.com.etyllica.keel.filter.validation.MinDensityValidation;
import br.com.etyllica.keel.filter.validation.MinDimensionValidation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TrackingMultiAreaApplication extends Application {

    protected Camera cam;

    protected BufferedImage buffer;
    private BufferedImageSource source = new BufferedImageSource();

    //Orange Marker
    private ColorFilter orangeFilter;
    //private Color orangeColor = new Color(165, 94, 74);
    private Color orangeColor = new Color(67, 81, 107);

    //Blue Marker
    private ColorFilter blueFilter;
    private Color blueColor = new Color(0, 153, 255);

    private MultiArea area = new MultiArea(5);

    private int tolerance = 10;
    private int minDensity = 12;
    private int minDimension = 37;
    private int maxDimension = 270;

    private MinDensityValidation densityValidation;
    private MinDimensionValidation minDimensionValidation;
    private MaxDimensionValidation maxDimensionValidation;

    private boolean hide = false;
    private boolean markers = true;

    private int xOffset = 0;
    private int yOffset = 0;

    protected Component screen;

    //Area Stuff
    private boolean foundTwoComponents = false;
    private int areaOver = -1;

    private Component c1 = null, c2 = null;
    private double densityC1 = 0;
    private double densityC2 = 0;
    private static final int minCollisionCount = 80;

    private List<Component> orangeComponents;
    private List<Component> blueComponents;

    public TrackingMultiAreaApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {

        loadingInfo = "Loading Images";

        screen = setupCamera();

        densityValidation = new MinDensityValidation(minDensity);
        minDimensionValidation = new MinDimensionValidation(minDimension);
        maxDimensionValidation = new MaxDimensionValidation(maxDimension);

        orangeFilter = setupFilter(orangeColor);
        orangeFilter.getSearchStrategy().addValidation(minDimensionValidation);
        orangeFilter.getSearchStrategy().addValidation(densityValidation);
        SoftFloodFillSearch search = (SoftFloodFillSearch) (orangeFilter.getSearchStrategy());
        search.setMinNeighbors(7);

        blueFilter = setupFilter(blueColor);
        blueFilter.getSearchStrategy().addValidation(minDimensionValidation);

        loadingInfo = "Configuring Filter";

        loading = 60;
        reset(cam.getBufferedImage());

        loading = 100;
    }

    protected Component setupCamera() {
        cam = new FakeCamera();

		/*cam.addImage("dumbbells/dumbbells1.png");
        cam.addImage("dumbbells/dumbbells2.png");*/
        ((FakeCamera) cam).addImage("dumbbells/dumbbells5.png");

        int w = cam.getBufferedImage().getWidth();
        int h = cam.getBufferedImage().getHeight();

        screen = new Component(0, 0, w, h);

        return screen;
    }

    private ColorFilter setupFilter(Color color) {
        final int MAGIC_NUMBER = 3;//Higher = Faster and less precise

        ColorFilter filter = new ColorFilter(screen.getW(), screen.getH(), color, tolerance);
        filter.getSearchStrategy().setBorder(MAGIC_NUMBER);
        filter.getSearchStrategy().setStep(2);
        filter.getSearchStrategy().addValidation(maxDimensionValidation);

        return filter;
    }

    protected void reset(BufferedImage b) {
        source.setImage(b);

        orangeComponents = orangeFilter.filter(source, screen);
        blueComponents = blueFilter.filter(source, screen);

        if (!orangeComponents.isEmpty()) {
            evaluateDensity(b);
        }

        if (foundTwoComponents) {

            area.generateArea(c1.getCenter(), c2.getCenter());
            areaOver = -1;

            //Verify Collisions
            for (Component component : blueComponents) {
                for (int i = 0; i < area.getAreas(); i++) {

                    int count = 0;

                    for (Point2D p : component.getPoints()) {
                        Polygon polygon = area.getPolygons()[i];
                        if (polygon.contains(p.getX(), p.getY())) {
                            count++;
                        }
                    }

                    if (count >= minCollisionCount) {
                        areaOver = i;
                        break;
                    }
                }
            }
        }

        buffer = b;
    }

    private void evaluateDensity(BufferedImage b) {
        densityC1 = 0;
        densityC2 = 0;

        int found = 0;

        for (Component component : orangeComponents) {

            if (nonSquared(component) || !hasCenterColor(component, b)) {
                continue;
            }

            double dens = component.getDensity();

            if (dens > densityC1) {
                c2 = c1;
                densityC2 = densityC1;
                c1 = component;
                densityC1 = dens;
                found++;
            } else if (dens > densityC2) {
                c2 = component;
                densityC2 = dens;
                found++;
            }
        }

        foundTwoComponents = found >= 2;
    }

    private boolean nonSquared(Component component) {
        return component.getW() > component.getH() * 1.5;
    }

    private boolean hasCenterColor(Component component, BufferedImage b) {
        Point2D center = component.getCenter();
        int rgb = b.getRGB((int) center.getX(), (int) center.getY());
        return ColorHelper.isColor(orangeFilter.getColor(), rgb, 8);
    }

    @Override
    public void updateMouse(PointerEvent event) {

        if (event.isButtonDown(MouseEvent.MOUSE_BUTTON_LEFT)) {
            orangeColor = pickColor(event.getX(), event.getY());
            orangeFilter.setColor(orangeColor);

            System.out.println(orangeColor.getRed());
            System.out.println(orangeColor.getGreen());
            System.out.println(orangeColor.getBlue());
            System.out.println("---------");
        }

        if (event.isButtonDown(MouseEvent.MOUSE_BUTTON_RIGHT)) {
            blueColor = pickColor(event.getX(), event.getY());
            blueFilter.setColor(blueColor);

            System.out.println(blueColor.getRed());
            System.out.println(blueColor.getGreen());
            System.out.println(blueColor.getBlue());
            System.out.println("---------");
        }
    }

    private Color pickColor(int px, int py) {
        return new Color(buffer.getRGB(px, py));
    }

    @Override
    public void updateKeyboard(KeyEvent event) {

        if (event.isKeyDown(KeyEvent.VK_H)) {
            hide = !hide;
        }

        if (event.isKeyDown(KeyEvent.VK_J)) {
            markers = !markers;
        }

        //Change Tolerance
        if (event.isKeyUp(KeyEvent.VK_EQUALS)) {
            tolerance++;
            orangeFilter.setTolerance(tolerance);
        } else if (event.isKeyUp(KeyEvent.VK_MINUS)) {
            tolerance--;
            orangeFilter.setTolerance(tolerance);
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
            minDimensionValidation.setDimension(minDimension);
        } else if (event.isKeyUp(KeyEvent.VK_K)) {
            minDimension--;
            minDimensionValidation.setDimension(minDimension);
        }

        updateCameraInput(event);
    }

    protected void updateCameraInput(KeyEvent event) {
        if (event.isKeyUp(KeyEvent.VK_RIGHT_ARROW)) {
            ((FakeCamera) cam).nextFrame();
        } else if (event.isKeyUp(KeyEvent.VK_LEFT_ARROW)) {
            ((FakeCamera) cam).previousFrame();
        }
    }

    @Override
    public void draw(Graphics g) {

        reset(cam.getBufferedImage());

        if (!hide) {
            g.drawImage(buffer, xOffset, yOffset);
        }

        g.setColor(orangeColor);
        g.fillRect(0, 0, 60, 80);

        g.setColor(blueColor);
        g.fillRect(w - 60, 0, 60, 80);

        g.setColor(Color.WHITE);

        if (markers) {

            g.drawStringShadow("Tol: " + Integer.toString(tolerance), 10, 80);
            g.drawStringShadow("Den: " + Integer.toString(minDensity), 10, 100);
            g.drawStringShadow("Dim: " + Integer.toString(minDimension), 10, 120);

            g.setColor(SVGColor.ORANGE);

            //Draw OrangeComponents
            if (orangeComponents != null) {
                for (Component component : orangeComponents) {
                    g.drawPolygon(component.getBoundingBox());
                    g.drawStringShadow(component.getW() + "x" + component.getH(), component.getRectangle());
                    g.drawStringShadow(Double.toString(component.getDensity()), component.getX(), component.getY() + 25, component.getW(), component.getH());

                }
            }

            if (foundTwoComponents) {

                //Draw Areas
                g.setAlpha(50);
                g.setColor(Color.BLUE);

                for (int i = 0; i < area.getAreas(); i++) {
                    Polygon polygon = area.getPolygons()[i];

                    if (areaOver != i) {
						/*g.setColor(Color.GREEN);
						g.drawRect(area.getLayers()[i]);*/

                        g.setColor(SVGColor.BLUE);
                        g.drawPolygon(polygon);
                    } else {
                        g.fillPolygon(polygon);
                    }

                }

                g.resetOpacity();
            }

            if (areaOver >= 0)
                g.drawStringShadowX(Integer.toString(areaOver + 1), 300);

            if (blueComponents != null) {
                for (Component component : blueComponents) {
                    g.drawPolygon(component.getBoundingBox());
                }
            }

        }
    }

}