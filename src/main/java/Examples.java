

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.medium.skin.SkinGraphic;

public class Examples extends Etyl {

    public Examples() {
        super(800, 480);
        //super(256,256);
    }

    public static void main(String[] args) {
        Examples example = new Examples();
        example.init();
    }

    public Application startApplication() {

        initialSetup("../../");

        //Basic Examples

        //return new QuickHullExampleApplication(w,h);

        //return new ColorTrackingApplication(w,h);

        //return new SimpleCam(w,h);

        //return new MagicWandStatic(w,h);


        //Medium Examples

        //return new AirWrite(w,h);

        //return new AugmentedRealityStatic(w,h);

        //return new MagicWand(w,h);

        //return new BarCodeExample(w,h);

        //return new FaceStatic(w,h);

        //return new PirateHatApplication(w,h);
        //return new FaceSkinFilter(w,h);

        //return new HollowController(w,h);
        //return new HollowTest(w,h);

        //return new FaceApplication(w,h);

        //return new FaceSampledMultiFilterStatic(w,h);

        //return new FaceSampledStatic(w,h);

        //return new FaceSampledReal(w,h);

        //return new MagicWand(w,h);

        //return new MagicWandStatic(w,h);

        //return new BarCodeExample(w,h);

        //return new CaptchaCleaner(w,h);

        return new SkinGraphic(w, h);

        //return new OGRApplication(w,h);

    }

}
