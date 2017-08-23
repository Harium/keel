package examples.basic.circle;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;

public class CircleExample extends Etyl {

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
