package examples.basic.subcomponent;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;

public class SubcomponentTrackingExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public SubcomponentTrackingExample() {
        super(800, 480);
    }

    public static void main(String[] args) {
        SubcomponentTrackingExample example = new SubcomponentTrackingExample();
        example.init();
    }

    public Application startApplication() {
        return new SubcomponentApplication(w, h);
    }

}
