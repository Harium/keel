package examples.basic.circle;

import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;

public class CircleExample extends Etyllica {

    private static final long serialVersionUID = 1L;

    public CircleExample() {
        super(640, 480);
    }

    public static void main(String[] args) {
        CircleExample example = new CircleExample();
        example.init();
    }

    public Application startApplication() {
        initialSetup("../");

        return new CircleApplication(w, h);
    }

}
