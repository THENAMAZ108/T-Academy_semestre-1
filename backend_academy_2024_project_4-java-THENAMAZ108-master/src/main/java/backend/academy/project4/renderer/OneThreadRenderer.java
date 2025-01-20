package backend.academy.project4.renderer;

import backend.academy.project4.model.FractalImage;
import backend.academy.project4.transformation.TransformationVariation;
import java.util.List;

public class OneThreadRenderer extends AbstractRenderer {

    public OneThreadRenderer(
        int samples,
        int iterPerSample,
        int symmetry,
        List<TransformationVariation> variations
    ) {
        super(samples, iterPerSample, symmetry, variations);
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        List<TransformationVariation> variations,
        int symmetry,
        int samples,
        short iterPerSample
    ) {
        for (int num = 0; num < samples; ++num) {
            renderOneSample(canvas);
        }
        return canvas;
    }
}
