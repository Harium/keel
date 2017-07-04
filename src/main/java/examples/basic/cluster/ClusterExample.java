package examples.basic.cluster;


import br.com.etyllica.Etyllica;
import br.com.etyllica.commons.context.Application;

public class ClusterExample extends Etyllica {

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
