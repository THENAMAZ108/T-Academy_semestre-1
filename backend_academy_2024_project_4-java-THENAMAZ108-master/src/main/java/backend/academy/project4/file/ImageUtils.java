package backend.academy.project4.file;

import backend.academy.project4.model.FractalImage;
import backend.academy.project4.model.Pixel;
import java.awt.image.BufferedImage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageUtils {
    private static final int MAX = 255;
    private static final int A = 24;
    private static final int R = 16;
    private static final int G = 8;
    private static final int BLACK = MAX << A;

    public static BufferedImage convertFractalImageToBufferedImage(FractalImage image) {
        BufferedImage renderedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.pixel(x, y);
                if (pixel.hitCount() == 0) {
                    renderedImage.setRGB(x, y, BLACK);
                    continue;
                }
                int color = (MAX << A) | (pixel.r() << R) | (pixel.g() << G) | pixel.b();
                renderedImage.setRGB(x, y, color);
            }
        }

        return renderedImage;
    }
}
