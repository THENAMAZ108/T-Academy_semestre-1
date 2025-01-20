package backend.academy.project4.processor;

import backend.academy.project4.model.FractalImage;
import backend.academy.project4.model.Pixel;

public class GammaCorrectionImageProcessor implements ImageProcessor {
    private static final double GAMMA = 2.2;

    @Override
    public void process(FractalImage image) throws NullPointerException {
        double max = 0;
        for (int x = 0; x < image.width(); ++x) {
            for (int y = 0; y < image.height(); ++y) {
                Pixel pixel = image.pixel(x, y);
                if (pixel.hitCount() != 0) {
                    image.setPixel(new Pixel(pixel.r(), pixel.g(), pixel.b(), Math.log10(pixel.hitCount()),
                        pixel.hitCount()
                    ), x, y);
                }
                max = Math.max(image.pixel(x, y).normal(), max);
            }
        }

        for (int x = 0; x < image.width(); ++x) {
            for (int y = 0; y < image.height(); ++y) {
                Pixel pixel = image.pixel(x, y);
                double normal = pixel.normal() / max;
                image.setPixel(new Pixel(
                    (int) (pixel.r() * Math.pow(normal, (1 / GAMMA))),
                    (int) (pixel.g() * Math.pow(normal, (1 / GAMMA))),
                    (int) (pixel.b() * Math.pow(normal, (1 / GAMMA))),
                    normal,
                    pixel.hitCount()
                ), x, y);
            }
        }
    }
}
