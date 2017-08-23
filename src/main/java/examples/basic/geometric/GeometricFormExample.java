package examples.basic.geometric;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;

public class GeometricFormExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public GeometricFormExample() {
        super(800, 480);
    }

    public static void main(String[] args) {
        GeometricFormExample example = new GeometricFormExample();
        example.init();
    }

    public Application startApplication() {

        //return new GeometricFormApplication(w,h);
        return new ColoredGeometricFormApplication(w, h);
    }

}
