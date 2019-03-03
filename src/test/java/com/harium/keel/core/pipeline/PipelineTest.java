package com.harium.keel.core.pipeline;

import com.harium.keel.core.source.ImageSource;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PipelineTest {

    Pipeline<Object, Object> pipeline;

    @Before
    public void setUp() {
        pipeline = new Pipeline<>();
    }

    @Test
    public void testProcess() {
        DummyPass firstPass = new DummyPass();
        String one = "1";

        pipeline.add(one, firstPass);
        pipeline.add("2", new DummyPass());

        MatrixSource source = new MatrixSource(2, 2);
        Object result = pipeline.process(source, null);
        Assert.assertNotNull(result);

        List<PipelinePass<?, ?>> passes = pipeline.getPasses();
        Assert.assertEquals(2, passes.size());

        Assert.assertEquals(one, pipeline.getNames().get(firstPass));
    }

    @Test
    public void testStopProcess() {
        pipeline.add("1", new DummyPass());
        pipeline.add("2", new ErrorPass());
        pipeline.add("3", new DummyPass());

        MatrixSource source = new MatrixSource(2, 2);
        Object result = pipeline.process(source, null);
        Assert.assertNull(result);

        List<PipelinePass<?, ?>> passes = pipeline.getPasses();
        Assert.assertEquals(3, passes.size());
    }

    public static class DummyPass implements PipelinePass<Object, Object> {
        @Override
        public Object process(ImageSource source, Object feature) {
            return new Object();
        }
    }

    public static class ErrorPass implements PipelinePass<Object, Object> {
        @Override
        public Object process(ImageSource source, Object feature) {
            return Pipeline.ERROR;
        }
    }

}
