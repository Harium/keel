package examples.basic.hull;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;

public class QuickHullExample extends Etyllica {

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
