package backend.academy.project4;

import backend.academy.project4.file.FormatImageSaver;
import backend.academy.project4.file.ImageFormat;
import backend.academy.project4.file.ImageSaver;
import backend.academy.project4.model.FractalImage;
import backend.academy.project4.renderer.MultiThreadRenderer;
import backend.academy.project4.renderer.OneThreadRenderer;
import backend.academy.project4.renderer.Renderer;
import backend.academy.project4.transformation.Transformation;
import backend.academy.project4.transformation.TransformationUtils;
import backend.academy.project4.transformation.TransformationVariation;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalImageGenerationRunner {

    private static final Logger LOGGER = Logger.getLogger(FractalImageGenerationRunner.class.getName());

    private static final String AND = " and ";

    private static final int MIN_WIDTH = 100;
    private static final int MAX_WIDTH = 10000;
    private static final int MIN_HEIGHT = 100;
    private static final int MAX_HEIGHT = 10000;
    private static final int MIN_SAMPLES = 1;
    private static final int MAX_SAMPLES = 10000;
    private static final int MIN_ITER_PER_SAMPLE = 1;
    private static final int MAX_ITER_PER_SAMPLE = 100000;
    private static final int MIN_SYMMETRY = 1;
    private static final int MAX_SYMMETRY = 100;
    private static final int MIN_THREADS = 1;
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;

    public static void run() {
        Properties properties = loadProperties();

        try {
            int width = Integer.parseInt(properties.getProperty("width"));
            int height = Integer.parseInt(properties.getProperty("height"));
            int samples = Integer.parseInt(properties.getProperty("samples"));
            short iterPerSample = Short.parseShort(properties.getProperty("iterations_per_sample"));
            int symmetry = Integer.parseInt(properties.getProperty("symmetry"));
            String transformationClassName = properties.getProperty("transformation");
            String outputPath = properties.getProperty("output_path");
            String outputFormat = properties.getProperty("output_format");
            int threads = Integer.parseInt(properties.getProperty("threads"));

            validateParameters(width, height, samples, iterPerSample, symmetry, threads);

            Transformation transformation =
                (Transformation) Class.forName(transformationClassName).getDeclaredConstructor().newInstance();
            List<TransformationVariation> variations = TransformationUtils.getVariations(transformation);

            FractalImage canvas = FractalImage.create(width, height);

            Renderer renderer;
            if (threads > 1) {
                renderer = new MultiThreadRenderer(samples, iterPerSample, symmetry, variations, threads);
            } else {
                renderer = new OneThreadRenderer(samples, iterPerSample, symmetry, variations);
            }

            FractalImage image = renderer.render(canvas, variations, symmetry, samples, iterPerSample);

            ImageSaver saver = new FormatImageSaver(ImageFormat.valueOf(outputFormat.toUpperCase()));
            saver.save(image, Path.of(outputPath));

            LOGGER.info("Rendering completed and image saved to " + outputPath);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to render image", e);
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input =
                 FractalImageGenerationRunner.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                LOGGER.severe("Unable to find config.properties");
                return properties;
            }
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error loading properties file", ex);
        }
        return properties;
    }

    private static void validateParameters(
        int width,
        int height,
        int samples,
        short iterPerSample,
        int symmetry,
        int threads
    ) {
        if (width < MIN_WIDTH || width > MAX_WIDTH) {
            throw new IllegalArgumentException("Width must be between " + MIN_WIDTH + AND + MAX_WIDTH);
        }
        if (height < MIN_HEIGHT || height > MAX_HEIGHT) {
            throw new IllegalArgumentException("Height must be between " + MIN_HEIGHT + AND + MAX_HEIGHT);
        }
        if (samples < MIN_SAMPLES || samples > MAX_SAMPLES) {
            throw new IllegalArgumentException("Samples must be between " + MIN_SAMPLES + AND + MAX_SAMPLES);
        }
        if (iterPerSample < MIN_ITER_PER_SAMPLE || iterPerSample > MAX_ITER_PER_SAMPLE) {
            throw new IllegalArgumentException(
                "Iterations per sample must be between " + MIN_ITER_PER_SAMPLE + AND + MAX_ITER_PER_SAMPLE);
        }
        if (symmetry < MIN_SYMMETRY || symmetry > MAX_SYMMETRY) {
            throw new IllegalArgumentException("Symmetry must be between " + MIN_SYMMETRY + AND + MAX_SYMMETRY);
        }
        if (threads < MIN_THREADS || threads > MAX_THREADS) {
            throw new IllegalArgumentException("Threads must be between " + MIN_THREADS + AND + MAX_THREADS);
        }
    }
}
