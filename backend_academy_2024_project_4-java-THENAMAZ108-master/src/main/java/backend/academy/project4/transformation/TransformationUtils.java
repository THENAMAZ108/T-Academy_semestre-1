package backend.academy.project4.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.experimental.UtilityClass;

@SuppressWarnings("MultipleVariableDeclarations")
@UtilityClass
public class TransformationUtils {
    private static final Random RANDOM = new Random();
    private static final int COLOR_LIMIT = 256;
    private static final double BOUND = 2;
    private static final int VARIATIONS_AMOUNT = 16;

    public static List<TransformationVariation> getVariations(Transformation transformation) {
        List<TransformationVariation> transformationVariations = new ArrayList<>(VARIATIONS_AMOUNT);
        for (int i = 0; i < VARIATIONS_AMOUNT; ++i) {
            Color color = getRandomColor();
            ShiftTransformation shift = getRandomShift();
            transformationVariations.add(new TransformationVariation(transformation, shift, color.r, color.g, color.b));
        }
        return transformationVariations;
    }

    private static Color getRandomColor() {
        return new Color(RANDOM.nextInt(COLOR_LIMIT), RANDOM.nextInt(COLOR_LIMIT), RANDOM.nextInt(COLOR_LIMIT));
    }

    private static ShiftTransformation getRandomShift() {
        double a = 0, b = 0, c, d = 0, e = 0, f;
        boolean areNotCorrect = true;
        while (areNotCorrect) {
            a = RANDOM.nextDouble(-1, 1);
            b = RANDOM.nextDouble(-1, 1);
            d = RANDOM.nextDouble(-1, 1);
            e = RANDOM.nextDouble(-1, 1);
            areNotCorrect = a * a + d * d >= 1;
            areNotCorrect |= b * b + e * e >= 1;
            areNotCorrect |= a * a + d * d + b * b + e * e >= 1 + (a * e - b * d) * (a * e - b * d);
        }
        c = RANDOM.nextDouble(-1 * BOUND, BOUND);
        f = RANDOM.nextDouble(-1 * BOUND, BOUND);
        return new ShiftTransformation(a, b, c, d, e, f);
    }

    private record Color(int r, int g, int b) {
    }
}
