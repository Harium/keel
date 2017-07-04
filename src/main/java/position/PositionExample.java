package position;

import br.com.etyllica.Etyllica;
import br.com.etyllica.commons.context.Application;
import examples.basic.circle.CircleApplication;
import position.application.SphereApplication;

public class PositionExample extends Etyllica {

    private static final long serialVersionUID = 1L;

    public PositionExample() {
        super(640, 480);
    }

    public static void main(String[] args) {
        PositionExample example = new PositionExample();
        example.init();
    }

    public Application startApplication() {
        initialSetup("../");

        return new SphereApplication(w, h);
    }

}
