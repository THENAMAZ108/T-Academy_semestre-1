package backend.academy.project4.file;

import backend.academy.project4.model.FractalImage;
import java.nio.file.Path;

public interface ImageSaver {
    void save(FractalImage image, Path path);
}

