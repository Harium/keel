package examples.medium;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.medium.ogr.OGRApplication;

public class OGRExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public OGRExample() {
        super(640, 480);
    }

    public static void main(String[] args) {
        OGRExample finder = new OGRExample();
        finder.init();
    }

    public Application startApplication() {
        initialSetup("");
        return new OGRApplication(w, h);
    }

}
