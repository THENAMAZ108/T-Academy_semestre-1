package backend.academy.project4.transformation;

import backend.academy.project4.model.Point;

public class LinearTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        return point;
    }
}
