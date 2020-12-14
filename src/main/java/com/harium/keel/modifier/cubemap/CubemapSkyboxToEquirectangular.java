package com.harium.keel.modifier.cubemap;

import com.harium.keel.core.Modifier;
import com.harium.keel.core.interpolation.Algorithm;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.effect.Crop;

public class CubemapSkyboxToEquirectangular implements Modifier<ImageSource, ImageSource> {

    private final Crop crop = new Crop();
    private final CubemapSlicesToEquirectangular cubemapToEquirectangular = new CubemapSlicesToEquirectangular();

    @Override
    public ImageSource apply(ImageSource skybox) {
        int sliceWidth = skybox.getWidth() / 4;
        int sliceHeight = skybox.getHeight() / 3;
        crop.width(sliceWidth).height(sliceHeight);

        ImageSource top = crop.position(sliceWidth, 0).apply(skybox);
        ImageSource left = crop.position(0, sliceHeight).apply(skybox);
        ImageSource front = crop.position(sliceWidth, sliceHeight).apply(skybox);
        ImageSource right = crop.position(sliceWidth * 2, sliceHeight).apply(skybox);
        ImageSource back = crop.position(sliceWidth * 3, sliceHeight).apply(skybox);
        ImageSource bottom = crop.position(sliceWidth, sliceHeight*2).apply(skybox);

        ImageSource[] slices = new ImageSource[6];
        slices[CubemapSlicesToEquirectangular.TOP] = top;
        slices[CubemapSlicesToEquirectangular.LEFT] = left;
        slices[CubemapSlicesToEquirectangular.FRONT] = front;
        slices[CubemapSlicesToEquirectangular.RIGHT] = right;
        slices[CubemapSlicesToEquirectangular.BACK] = back;
        slices[CubemapSlicesToEquirectangular.BOTTOM] = bottom;

        return cubemapToEquirectangular.apply(slices);
    }

    public CubemapSkyboxToEquirectangular using(Algorithm algorithm) {
        cubemapToEquirectangular.using(algorithm);
        return this;
    }

    public CubemapSkyboxToEquirectangular width(int width) {
        cubemapToEquirectangular.width(width);
        cubemapToEquirectangular.height = width / 2;
        return this;
    }

}
