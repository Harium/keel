package examples.medium.middleline;


import br.com.etyllica.Etyllica;
import br.com.etyllica.core.context.Application;

public class MiddleLineExample extends Etyllica {

    private static final long serialVersionUID = 1L;

    public MiddleLineExample() {
        super(800, 480);
    }

    public static void main(String[] args) {
        MiddleLineExample example = new MiddleLineExample();
        example.init();
    }

    public Application startApplication() {
        return new MiddleLineApplication(w, h);
    }

}
