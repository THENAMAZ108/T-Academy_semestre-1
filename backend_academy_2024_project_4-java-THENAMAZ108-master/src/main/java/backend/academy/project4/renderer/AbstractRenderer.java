package backend.academy.project4.renderer;

import backend.academy.project4.model.FractalImage;
import backend.academy.project4.model.Pixel;
import backend.academy.project4.model.Point;
import backend.academy.project4.transformation.TransformationVariation;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static backend.academy.project4.renderer.RendererUtils.MULTIPLIER;
import static backend.academy.project4.renderer.RendererUtils.X_LIMIT;
import static backend.academy.project4.renderer.RendererUtils.Y_LIMIT;
import static backend.academy.project4.renderer.RendererUtils.getRandomPoint;
import static backend.academy.project4.renderer.RendererUtils.transform;


public abstract class AbstractRenderer implements Renderer {
    protected final int samples;
    protected final int iterPerSample;
    protected final int symmetry;
    protected final List<TransformationVariation> variations;

    public AbstractRenderer(int samples, int iterPerSample, int symmetry, List<TransformationVariation> variations) {
        this.samples = samples;
        this.iterPerSample = iterPerSample;
        this.symmetry = symmetry;
        this.variations = variations;
    }

    protected void renderOneSample(FractalImage canvas) {
        Point point = getRandomPoint();

        for (int step = 0; step < iterPerSample; ++step) {
            TransformationVariation variation = variations.get(ThreadLocalRandom.current().nextInt(variations.size()));
            point = transform(point, variation);

            for (int s = 0; s < symmetry; ++s) {
                double theta = s * Math.PI * 2 / symmetry;
                var rotadedPoint = rotate(point, theta);

                int x = (int)
                        (canvas.width() - (X_LIMIT - rotadedPoint.x()) / (X_LIMIT * MULTIPLIER) * canvas.width());
                int y = (int)
                        (canvas.height() - (Y_LIMIT - rotadedPoint.y()) / (Y_LIMIT * MULTIPLIER) * canvas.height());

                Pixel pixel = canvas.pixel(x, y);

                if (pixel == null) {
                    continue;
                }

                Pixel newPixel;
                if (pixel.hitCount() == 0) {
                    newPixel = new Pixel(variation.red(), variation.green(), variation.blue(), 0, 1);
                } else {
                    newPixel = new Pixel(
                            (variation.red() + pixel.r()) / 2,
                            (variation.green() + pixel.g()) / 2,
                            (variation.blue() + pixel.b()) / 2,
                            pixel.normal(),
                            pixel.hitCount() + 1
                    );
                }
                synchronized (canvas) {
                    canvas.setPixel(newPixel, x, y);
                }
            }
        }
    }

    @Override
    public abstract FractalImage render(
            FractalImage canvas,
            List<TransformationVariation> variations,
            int symmetry,
            int samples,
            short iterPerSample
    );

    private static Point rotate(Point point, double theta) {
        double deltaX = point.x();
        double deltaY = point.y();

        double rotatedX = deltaX * Math.cos(theta) - deltaY * Math.sin(theta);
        double rotatedY = deltaX * Math.sin(theta) + deltaY * Math.cos(theta);

        return new Point(rotatedX, rotatedY);
    }
}
