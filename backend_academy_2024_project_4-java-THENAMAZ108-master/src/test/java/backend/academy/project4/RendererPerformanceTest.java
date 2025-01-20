package backend.academy.project4;

import backend.academy.project4.file.FormatImageSaver;
import backend.academy.project4.file.ImageFormat;
import backend.academy.project4.file.ImageSaver;
import backend.academy.project4.model.FractalImage;
import backend.academy.project4.renderer.MultiThreadRenderer;
import backend.academy.project4.renderer.OneThreadRenderer;
import backend.academy.project4.transformation.HeartTransformation;
import backend.academy.project4.transformation.TransformationUtils;
import backend.academy.project4.transformation.TransformationVariation;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RendererPerformanceTest {

    private static final int WIDTH = 1920 * 2;
    private static final int HEIGHT = 1080 * 2;
    private static final int SAMPLES = 1000;
    private static final short ITER_PER_SAMPLE = 10000;
    private static final int SYMMETRY = 4;
    private static final List<TransformationVariation> VARIATIONS = TransformationUtils.getVariations(
        new HeartTransformation());

    @Test
    public void testOneThreadRendererPerformance() {
        FractalImage canvas = FractalImage.create(WIDTH, HEIGHT);

        OneThreadRenderer renderer = new OneThreadRenderer(SAMPLES, ITER_PER_SAMPLE, SYMMETRY, VARIATIONS);

        long startTime = System.nanoTime();
        FractalImage image = renderer.render(canvas, VARIATIONS, SYMMETRY, SAMPLES, ITER_PER_SAMPLE);
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        System.out.println("OneThreadRenderer execution time: " + duration / 1_000_000 + " ms");

        ImageSaver saver = new FormatImageSaver(ImageFormat.PNG);
        saver.save(image, Path.of("heart_one.png"));
    }

    @Test
    public void testMultiThreadRendererPerformance() {
        FractalImage canvas = FractalImage.create(WIDTH, HEIGHT);

        MultiThreadRenderer renderer = new MultiThreadRenderer(SAMPLES, ITER_PER_SAMPLE, SYMMETRY, VARIATIONS,
            Runtime.getRuntime().availableProcessors());

        long startTime = System.nanoTime();
        FractalImage image = renderer.render(canvas, VARIATIONS, SYMMETRY, SAMPLES, ITER_PER_SAMPLE);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("MultiThreadRenderer execution time: " + duration / 1_000_000 + " ms");

        ImageSaver saver = new FormatImageSaver(ImageFormat.PNG);
        saver.save(image, Path.of("heart_multi.png"));
    }
}

