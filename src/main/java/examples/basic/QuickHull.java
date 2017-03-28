package examples.basic;


import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.core.context.Application;
import examples.basic.application.QuickHullExampleApplication;

public class QuickHull extends EtyllicaApplet {

    private static final long serialVersionUID = 1L;

    public QuickHull() {
        super(800, 480);
    }

    public Application startApplication() {
        return new QuickHullExampleApplication(w, h);
    }

}
