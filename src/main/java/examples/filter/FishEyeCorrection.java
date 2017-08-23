package examples.filter;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.filter.application.FishEyeCorrectionApplication;

public class FishEyeCorrection extends Etyl {

    private static final long serialVersionUID = 1L;

    public FishEyeCorrection() {
        super(800, 480);
    }

    public static void main(String[] args) {
        FishEyeCorrection example = new FishEyeCorrection();
        example.init();
    }

    public Application startApplication() {

        initialSetup("../../../../");

        return new FishEyeCorrectionApplication(w, h);

    }

}