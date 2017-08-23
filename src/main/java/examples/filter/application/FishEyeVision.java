package examples.filter.application;

import br.com.etyllica.keel.image.SimpleFishEyeCorrectionFilter;
import com.harium.etyl.commons.context.Application;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.layer.BufferedLayer;
import com.harium.etyl.loader.image.ImageLoader;

import java.awt.image.BufferedImage;

public class FishEyeVision extends Application {

    private BufferedLayer layer;

    public FishEyeVision(int w, int h) {
        super(w, h);
    }

    @Override
    public void load() {

        loading = 0;

        BufferedImage image = ImageLoader.getInstance().getImage("public_domain/nice-hotel-in-bohol.jpg");
        BufferedImage afterFx = new SimpleFishEyeCorrectionFilter(4.90, 0.55).process(image);
        //BufferedImage afterFx = new SimpleFishEyeCorrectionFilter(7.90, 0.55).process(image);

        layer = new BufferedLayer(afterFx);
        layer.centralize(this);

        loading = 50;
    }

    @Override
    public void draw(Graphics g) {
        layer.draw(g);
    }

}
