package com.harium.keel.core.pipeline;

import com.harium.etyl.commons.graphics.Color;
import com.harium.keel.core.FilterPipeline;
import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSourceTest;
import com.harium.keel.feature.Feature;
import com.harium.keel.filter.ColorFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FilterPipelineTest {

    @Test
    public void testSimplestPipeline() {
        Feature screen = new Feature(2,2);
        ImageSource source = MatrixSourceTest.buildChecker();

        ColorFilter filter = new ColorFilter(screen.getWidth(), screen.getHeight(), Color.BLACK);
        filter.setBorder(0);

        FilterPipeline pipeline = FilterPipeline.build()
                .add(filter);

        List<Feature> result = (List<Feature>) pipeline.run(source, screen);
        Assert.assertEquals(2, result.size());
    }

}
