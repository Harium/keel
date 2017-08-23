package examples.basic.colortrack;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;

public class ColorTrackingExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public ColorTrackingExample() {
        super(800, 480);
    }

    public static void main(String[] args) {
        ColorTrackingExample example = new ColorTrackingExample();
        example.init();
    }

    public Application startApplication() {
        return new ColorTrackingApplication(w, h);
    }

}
