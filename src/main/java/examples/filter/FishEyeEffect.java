package examples.filter;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.filter.application.FishEyeVision;

public class FishEyeEffect extends Etyl {

    private static final long serialVersionUID = 1L;

    public FishEyeEffect() {
        super(800, 480);
    }

    public static void main(String[] args) {
        FishEyeEffect example = new FishEyeEffect();
        example.init();
    }

    public Application startApplication() {
        initialSetup("../../");
        return new FishEyeVision(w, h);
    }
}