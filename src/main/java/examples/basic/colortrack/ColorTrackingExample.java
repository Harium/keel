package examples.basic.colortrack;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;

public class ColorTrackingExample extends Etyllica {

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
