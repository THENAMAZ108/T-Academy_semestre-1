package backend.academy.project4.processor;

import backend.academy.project4.model.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
