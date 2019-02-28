package com.harium.keel.core.pipeline;

import com.harium.keel.core.source.ImageSource;

public interface PipelinePass<I, O> {

    O process(ImageSource source, I feature);

}
