package backend.academy.project4.renderer;

import backend.academy.project4.model.FractalImage;
import backend.academy.project4.transformation.TransformationVariation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadRenderer extends AbstractRenderer {

    private final int numThreads;

    public MultiThreadRenderer(
        int samples,
        int iterPerSample,
        int symmetry,
        List<TransformationVariation> variations,
        int numThreads
    ) {
        super(samples, iterPerSample, symmetry, variations);
        this.numThreads = numThreads;
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        List<TransformationVariation> variations,
        int symmetry,
        int samples,
        short iterPerSample
    ) {
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int num = 0; num < samples; ++num) {
            executorService.submit(() -> renderOneSample(canvas));
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.HOURS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return canvas;
    }
}
