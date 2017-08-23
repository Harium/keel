package examples.medium.skin;

import br.com.etyllica.keel.awt.camera.Camera;
import br.com.etyllica.keel.awt.camera.FakeCamera;
import br.com.etyllica.keel.awt.source.BufferedImageSource;
import br.com.etyllica.keel.core.helper.ColorHelper;
import br.com.etyllica.keel.core.strategy.SearchFilter;
import br.com.etyllica.keel.custom.AverageColorFilter;
import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.filter.ExpandableColorFilter;
import br.com.etyllica.keel.filter.HardColorFilter;
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
import java.util.*;

public class SimpleFaceFinderApplication extends Application {

    protected Camera cam = new FakeCamera();
    private BufferedImageSource source = new BufferedImageSource();

    private final int IMAGES_TO_LOAD = 20;

    private SkinColorFilter skinFilter;

    protected Component bestCandidate;
    private List<Component> skinComponents;
    private List<Component> darkComponents;
    private Map<Component, Integer> counts = new HashMap<Component, Integer>();
    protected List<Component> faceComponents = new ArrayList<Component>();

    private Component screen;

    private Color color = Color.BLACK;

    private boolean drawPoints = false;
    private boolean leftPoints = true;

    public SimpleFaceFinderApplication(int w, int h) {
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

        BufferedImage image = cam.getBufferedImage();
        source.setImage(image);

        //Define the area to search for elements
        int w = image.getWidth();
        int h = image.getHeight();

        screen = new Component(0, 0, w, h);
        skinFilter = new SkinColorFilter(w, h, new SkinColorKovacNewStrategy());
        HardColorFilter colorFilter = new HardColorFilter(w, h, new Color(40, 40, 40), 25);

        SearchFilter filter = skinFilter.getSearchStrategy();
        filter.setStep(2);
        filter.setBorder(20);

        //Remove components smaller than 20x20
        skinFilter.addValidation(new MinDimensionValidation(20));
        skinComponents = skinFilter.filter(source, screen);

        colorFilter.addValidation(new MinDimensionValidation(3));
        darkComponents = colorFilter.filter(source, screen);

        //Evaluate components
        //validateComponents();
        bestCandidate = evaluateComponent(skinComponents);

        Color faceColor = AverageColorFilter.filter(image, bestCandidate);

        ExpandableColorFilter featureFilter = new ExpandableColorFilter(w, h, faceColor, 30);
        //featureFilter.getSearchStrategy().setBorder(2);
        featureFilter.getSearchStrategy().setStep(4);
        featureFilter.addValidation(new MinDimensionValidation(2));

        faceComponents = featureFilter.filter(source, bestCandidate);

        //System.out.println("Fc "+faceComponents.size());
        color = randomColor();
    }

    private void validateComponents() {
        for (int i = skinComponents.size() - 1; i >= 0; i--) {
            Component component = skinComponents.get(i);

            //Vertical trim component
            //component = trim(component);

            //Remove components near from left border
            if (component.getX() < 20 + 10) {
                skinComponents.remove(i);
                continue;
            }

            if (component.getX() + component.getW() > h - 10) {
                skinComponents.remove(i);
                continue;
            }
        }
    }

    private Component evaluateComponent(List<Component> components) {
        int higher = 0;
        Component faceCandidate = components.get(0);

        for (Component component : components) {
            int count = 0;
            for (Component dc : darkComponents) {
                if (component.colide(dc)) {
                    count++;
                }
            }
            if (count > higher) {
                higher = count;
                faceCandidate = component;
            }
            counts.put(component, count);
        }

        return faceCandidate;
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

        g.setColor(color);
        drawComponent(g, bestCandidate);

        g.setColor(Color.RED);
        for (Component feature : faceComponents) {
            drawAllPoints(g, feature);
            g.drawRect(feature.getRectangle());
        }

        //Draw a red line around the components
        //drawComponents(g);

        //Draw dark components
        /*g.setStroke(new BasicStroke(3f));
		g.setColor(Color.BLACK);

		for(Component component:darkComponents) {
			g.drawRect(component.getRectangle());
		}*/
    }

    protected void drawComponents(Graphics g) {
        for (int i = 0; i < skinComponents.size(); i++) {
            Component component = skinComponents.get(i);

            g.setColor(color);
            drawComponent(g, component);
        }
    }

    protected void drawComponent(Graphics g, Component component) {
        //g.setStroke(new BasicStroke(3f));
        g.drawRect(component.getRectangle());

        g.setColor(Color.BLACK);

        int count = counts.get(component);

        g.drawString(Integer.toString(count), component.getRectangle());

        if (drawPoints) {
            drawPoints(g, component);
        }
    }

    public void drawPoints(Graphics g, Component component) {
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

    public void drawAllPoints(Graphics g, Component component) {
        for (Point2D point : component.getPoints()) {
            g.fillRect((int) point.getX(), (int) point.getY(), 1, 1);
        }
    }
}
