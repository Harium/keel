package examples.basic.geometric;


import br.com.etyllica.Etyllica;
import br.com.etyllica.commons.context.Application;

public class GeometricFormExample extends Etyllica {

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
