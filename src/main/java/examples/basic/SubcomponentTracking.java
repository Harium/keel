package examples.basic;


import br.com.etyllica.EtyllicaApplet;
import br.com.etyllica.core.context.Application;
import examples.basic.application.SubcomponentApplication;

public class SubcomponentTracking extends EtyllicaApplet {

    private static final long serialVersionUID = 1L;

    public SubcomponentTracking() {
        super(800, 480);
    }

    public Application startApplication() {
        return new SubcomponentApplication(w, h);
    }

}
