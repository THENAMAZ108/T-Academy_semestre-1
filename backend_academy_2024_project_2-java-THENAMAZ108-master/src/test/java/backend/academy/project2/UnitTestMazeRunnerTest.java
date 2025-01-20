package backend.academy.project2;

import backend.academy.project2.generator.BFSGenerator;
import backend.academy.project2.generator.Generator;
import backend.academy.project2.generator.KruskalGenerator;
import backend.academy.project2.solver.BFSSolver;
import backend.academy.project2.solver.DFSSolver;
import backend.academy.project2.solver.Solver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTestMazeRunnerTest {

    private final UnitTestMazeRunner unitTestMazeRunner = new UnitTestMazeRunner();

    @Test
    @DisplayName("Test isCorrectSize with valid sizes")
    void testIsCorrectSizeValid() {
        assertTrue(unitTestMazeRunner.isCorrectSize(5, 5));
        assertTrue(unitTestMazeRunner.isCorrectSize(1, 1));
    }

    @Test
    @DisplayName("Test isCorrectSize with invalid sizes")
    void testIsCorrectSizeInvalid() {
        assertFalse(unitTestMazeRunner.isCorrectSize(0, 5));
        assertFalse(unitTestMazeRunner.isCorrectSize(5, 0));
        assertFalse(unitTestMazeRunner.isCorrectSize(-1, -1));
    }

    @Test
    @DisplayName("Test isCorrectCoordinate with valid coordinates")
    void testIsCorrectCoordinateValid() {
        assertTrue(unitTestMazeRunner.isCorrectCoordinate(1, 1, 15, 15));
        assertTrue(unitTestMazeRunner.isCorrectCoordinate(6, 6, 15, 15));
    }

    @Test
    @DisplayName("Test isCorrectCoordinate with invalid coordinates")
    void testIsCorrectCoordinateInvalid() {
        assertFalse(unitTestMazeRunner.isCorrectCoordinate(8, 8, 15, 15));
        assertFalse(unitTestMazeRunner.isCorrectCoordinate(-1, 1, 15, 15));
        assertFalse(unitTestMazeRunner.isCorrectCoordinate(1, -1, 15, 15));
    }

    @Test
    @DisplayName("Test chooseGenerator with valid input")
    void testChooseGeneratorValid() {
        Generator kruskalGenerator = unitTestMazeRunner.chooseGenerator(UnitTestMazeRunner.KRUSKAL);
        assertInstanceOf(KruskalGenerator.class, kruskalGenerator);

        Generator bfsGenerator = unitTestMazeRunner.chooseGenerator(UnitTestMazeRunner.BFS);
        assertInstanceOf(BFSGenerator.class, bfsGenerator);
    }

    @Test
    @DisplayName("Test chooseGenerator with invalid input")
    void testChooseGeneratorInvalid() {
        Generator invalidGenerator = unitTestMazeRunner.chooseGenerator("invalid");
        assertNull(invalidGenerator);
    }

    @Test
    @DisplayName("Test chooseSolver with valid input")
    void testChooseSolverValid() {
        Solver dfsSolver = unitTestMazeRunner.chooseSolver(UnitTestMazeRunner.DFS);
        assertInstanceOf(DFSSolver.class, dfsSolver);

        Solver bfsSolver = unitTestMazeRunner.chooseSolver(UnitTestMazeRunner.BFS);
        assertInstanceOf(BFSSolver.class, bfsSolver);
    }

    @Test
    @DisplayName("Test chooseSolver with invalid input")
    void testChooseSolverInvalid() {
        Solver invalidSolver = unitTestMazeRunner.chooseSolver("invalid");
        assertNull(invalidSolver);
    }
}
