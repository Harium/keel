package examples.basic.hull;

import br.com.etyllica.keel.feature.Component;
import br.com.etyllica.keel.feature.hull.HullComponent;
import br.com.etyllica.keel.modifier.hull.FastConvexHullModifier;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.commons.event.MouseEvent;
import com.harium.etyl.commons.event.PointerEvent;
import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.linear.Point2D;

import java.awt.*;

public class QuickHullExampleApplication extends Application {

    private FastConvexHullModifier quickHullModifier;

    private Component component;

    private HullComponent convexHull = null;

    public QuickHullExampleApplication(int w, int h) {
        super(w, h);//Size of our application
    }

    @Override
    public void load() {

        //listOfPoints = new ArrayList<Point2D>();
        component = new Component();

        quickHullModifier = new FastConvexHullModifier();

        loading = 100;
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);

        g.setFontSize(18);

        g.drawStringX("Quick Hull Example", 40);

        g.drawStringX("Press Left Mouse Button to Add Points", 70);

        g.drawStringX("Press Right Mouse Button to Compute the Convex Hull", 90);

        for (Point2D point : component.getPoints()) {
            g.drawCircle(point, 5);
        }

        drawConvexHull(g);

    }

    private void drawConvexHull(Graphics g) {

        if (convexHull == null)
            return;

        Polygon polygon = convexHull.getPolygon();

        g.setColor(Color.BLACK);

        for (Point2D point : convexHull.asList()) {
            g.drawCircle(point, 5);
        }

        g.setColor(Color.KHAKI);
        g.drawPolygon(polygon);
        g.setColor(Color.BLACK);
        g.drawPolygon(polygon);
    }

    @Override
    public void updateMouse(PointerEvent event) {

        if (event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)) {
            //Add a new Point to the list
            component.add(new Point2D(event.getX(), event.getY()));
        }

        if (event.isButtonUp(MouseEvent.MOUSE_BUTTON_RIGHT)) {
            //Compute the Convex Hull
            convexHull = quickHullModifier.modify(component);
        }
    }
}
