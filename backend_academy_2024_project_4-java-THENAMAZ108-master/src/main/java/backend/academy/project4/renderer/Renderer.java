package backend.academy.project4.renderer;

import backend.academy.project4.model.FractalImage;
import backend.academy.project4.transformation.TransformationVariation;
import java.util.List;

@FunctionalInterface
public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        List<TransformationVariation> variations,
        int symmetry,
        int samples,
        short iterPerSample
    );
}

