package examples.basic.cluster;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;

public class ClusterExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public ClusterExample() {
        super(800, 480);
    }

    public static void main(String[] args) {
        ClusterExample example = new ClusterExample();
        example.init();
    }

    public Application startApplication() {
        return new ClusterApplication(w, h);
    }

}
