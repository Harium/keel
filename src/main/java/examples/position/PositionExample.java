package examples.position;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.position.application.SphereApplication;

public class PositionExample extends Etyl {

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
