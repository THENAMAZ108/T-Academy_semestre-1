package backend.academy.project4.file;

import backend.academy.project4.model.FractalImage;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class FormatImageSaver implements ImageSaver {

    private final ImageFormat format;

    @SneakyThrows
    @Override
    public void save(FractalImage image, Path path) {
        ImageIO.write(
            ImageUtils.convertFractalImageToBufferedImage(image), format.getFormatName(), path.toFile());
    }
}
