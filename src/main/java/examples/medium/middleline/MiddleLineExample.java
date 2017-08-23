package examples.medium.middleline;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;

public class MiddleLineExample extends Etyl {

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
