package examples.basic.hull;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;

public class QuickHullExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public QuickHullExample() {
        super(800, 480);
    }

    public static void main(String[] args) {
        QuickHullExample example = new QuickHullExample();
        example.init();
    }

    public Application startApplication() {
        return new QuickHullExampleApplication(w, h);
    }

}
