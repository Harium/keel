package examples.filter.application;

import br.com.etyllica.keel.image.SimpleFishEyeCorrectionFilter;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.layer.BufferedLayer;
import com.harium.etyl.loader.image.ImageLoader;

import java.awt.image.BufferedImage;

public class FishEyeCorrectionApplication extends Application {

    //private BufferedImage image;
    private BufferedLayer layer;

    public FishEyeCorrectionApplication(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {

        loading = 0;

        BufferedImage image = ImageLoader.getInstance().getImage("fisheye/398.jpg");
        BufferedImage corrected = new SimpleFishEyeCorrectionFilter(0.90).process(image);
        //BufferedImage corrected = new LensCorrectionFilter().process(image);


        layer = new BufferedLayer(corrected);
        layer.resize(640, 480);

        loading = 50;
    }

    @Override
    public void draw(Graphics g) {
        layer.draw(g);
    }

}
