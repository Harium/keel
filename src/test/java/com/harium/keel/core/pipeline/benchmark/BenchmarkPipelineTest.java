package com.harium.keel.core.pipeline.benchmark;

import com.harium.keel.core.pipeline.PipelineTest;
import com.harium.keel.core.source.MatrixSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BenchmarkPipelineTest {

    BenchmarkPipeline<Object, Object> pipeline;

    @Before
    public void setUp() {
        pipeline = new BenchmarkPipeline<>();
    }

    @Test
    public void testProcess() {
        pipeline.add("1", new PipelineTest.DummyPass());
        pipeline.add("2", new PipelineTest.DummyPass());

        MatrixSource source = new MatrixSource(2, 2);
        pipeline.process(source, null);

        Assert.assertNotNull(pipeline.getInfo());
        Assert.assertEquals(2, pipeline.getInfo().getPasses().size());

        List<BenchmarkPass> steps = pipeline.getInfo().getPasses();
        Assert.assertTrue(steps.get(0).getTime() >= 0);
        Assert.assertTrue(steps.get(1).getTime() >= 0);
    }

    @Test
    public void testStopProcess() {
        pipeline.add("1", new PipelineTest.DummyPass());
        pipeline.add("2", new PipelineTest.ErrorPass());
        pipeline.add("3", new PipelineTest.DummyPass());

        MatrixSource source = new MatrixSource(2, 2);
        pipeline.process(source, null);

        Assert.assertNotNull(pipeline.getInfo());
        Assert.assertEquals(2, pipeline.getInfo().getPasses().size());

        List<BenchmarkPass> passes = pipeline.getInfo().getPasses();
        Assert.assertTrue(passes.get(0).getTime() >= 0);
        Assert.assertTrue(passes.get(1).getTime() >= 0);
    }


}
