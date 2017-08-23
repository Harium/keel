package examples.hard;


import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.hard.microscopy.EmptyWellPlateApplication;

public class MicroscopyWellPlate extends Etyl {

    private static final long serialVersionUID = 1L;

    public MicroscopyWellPlate() {
        super(1280, 720);
    }

    public static void main(String[] args) {
        MicroscopyWellPlate example = new MicroscopyWellPlate();
        example.init();
    }

    public Application startApplication() {
        initialSetup("../../");

        return new EmptyWellPlateApplication(w, h);

    }

}
