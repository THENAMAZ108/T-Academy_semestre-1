package backend.academy.project2;

import backend.academy.project2.maze.Cell;
import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import backend.academy.project2.renderer.CIRenderer;
import backend.academy.project2.renderer.Renderer;
import backend.academy.project2.solver.BFSSolver;
import backend.academy.project2.solver.DFSSolver;
import backend.academy.project2.solver.Solver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MazeProgramTest {

    private static final String BLACK_BACK = "\33[40m";
    private static final String MAGENTA_BACK = "\33[45m";
    private static final String WHITESPACE = "   ";
    private static final String RESET = "\33[0m";

    private static final int ZERO = 0;
    private static final int TWO = 2;
    private static final int SEVEN = 7;

    @Test
    @DisplayName("Test correct maze solving")
    void testSolving() {
        // given
        Cell[][] grid = new Cell[][] {
            new Cell[] {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL),
                new Cell(0, 2, Cell.Type.WALL), new Cell(0, 3, Cell.Type.WALL),
                new Cell(0, 4, Cell.Type.WALL), new Cell(0, 5, Cell.Type.WALL),
                new Cell(0, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(1, 0, Cell.Type.WALL), new Cell(1, 1, Cell.Type.PASSAGE),
                new Cell(1, 2, Cell.Type.PASSAGE), new Cell(1, 3, Cell.Type.PASSAGE),
                new Cell(1, 4, Cell.Type.PASSAGE), new Cell(1, 5, Cell.Type.PASSAGE),
                new Cell(1, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(2, 0, Cell.Type.WALL), new Cell(2, 1, Cell.Type.WALL),
                new Cell(2, 2, Cell.Type.WALL), new Cell(2, 3, Cell.Type.WALL),
                new Cell(2, 4, Cell.Type.WALL), new Cell(2, 5, Cell.Type.PASSAGE),
                new Cell(2, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(3, 0, Cell.Type.WALL), new Cell(3, 1, Cell.Type.PASSAGE),
                new Cell(3, 2, Cell.Type.PASSAGE), new Cell(3, 3, Cell.Type.PASSAGE),
                new Cell(3, 4, Cell.Type.PASSAGE), new Cell(3, 5, Cell.Type.PASSAGE),
                new Cell(3, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(4, 0, Cell.Type.WALL), new Cell(4, 1, Cell.Type.PASSAGE),
                new Cell(4, 2, Cell.Type.WALL), new Cell(4, 3, Cell.Type.PASSAGE),
                new Cell(4, 4, Cell.Type.WALL), new Cell(4, 5, Cell.Type.PASSAGE),
                new Cell(4, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(5, 0, Cell.Type.WALL), new Cell(5, 1, Cell.Type.PASSAGE),
                new Cell(5, 2, Cell.Type.WALL), new Cell(5, 3, Cell.Type.PASSAGE),
                new Cell(5, 4, Cell.Type.WALL), new Cell(5, 5, Cell.Type.PASSAGE),
                new Cell(5, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(6, 0, Cell.Type.WALL), new Cell(6, 1, Cell.Type.WALL),
                new Cell(6, 2, Cell.Type.WALL), new Cell(6, 3, Cell.Type.WALL),
                new Cell(6, 4, Cell.Type.WALL), new Cell(6, 5, Cell.Type.WALL),
                new Cell(6, 6, Cell.Type.WALL)}
        };

        Maze maze = new Maze(SEVEN, SEVEN, grid);
        Solver dfsSolver = new DFSSolver();
        Solver bfsSolver = new BFSSolver();

        assertDoesNotThrow(() -> {
            dfsSolver.solve(maze, new Coordinate(ZERO, ZERO), new Coordinate(TWO, ZERO));
        });
        assertDoesNotThrow(() -> {
            bfsSolver.solve(maze, new Coordinate(ZERO, ZERO), new Coordinate(TWO, ZERO));
        });
    }

    @Test
    @DisplayName("Test correct maze rendering")
    void testRendering() {
        // given
        Cell[][] grid = new Cell[][] {
            new Cell[] {new Cell(0, 0, Cell.Type.WALL), new Cell(0, 1, Cell.Type.WALL),
                new Cell(0, 2, Cell.Type.WALL), new Cell(0, 3, Cell.Type.WALL),
                new Cell(0, 4, Cell.Type.WALL), new Cell(0, 5, Cell.Type.WALL),
                new Cell(0, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(1, 0, Cell.Type.WALL), new Cell(1, 1, Cell.Type.PASSAGE),
                new Cell(1, 2, Cell.Type.WALL), new Cell(1, 3, Cell.Type.PASSAGE),
                new Cell(1, 4, Cell.Type.WALL), new Cell(1, 5, Cell.Type.PASSAGE),
                new Cell(1, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(2, 0, Cell.Type.WALL), new Cell(2, 1, Cell.Type.PASSAGE),
                new Cell(2, 2, Cell.Type.PASSAGE), new Cell(2, 3, Cell.Type.PASSAGE),
                new Cell(2, 4, Cell.Type.WALL), new Cell(2, 5, Cell.Type.PASSAGE),
                new Cell(2, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(3, 0, Cell.Type.WALL), new Cell(3, 1, Cell.Type.WALL),
                new Cell(3, 2, Cell.Type.WALL), new Cell(3, 3, Cell.Type.PASSAGE),
                new Cell(3, 4, Cell.Type.WALL), new Cell(3, 5, Cell.Type.PASSAGE),
                new Cell(3, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(4, 0, Cell.Type.WALL), new Cell(4, 1, Cell.Type.PASSAGE),
                new Cell(4, 2, Cell.Type.PASSAGE), new Cell(4, 3, Cell.Type.PASSAGE),
                new Cell(4, 4, Cell.Type.PASSAGE), new Cell(4, 5, Cell.Type.PASSAGE),
                new Cell(4, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(5, 0, Cell.Type.WALL), new Cell(5, 1, Cell.Type.PASSAGE),
                new Cell(5, 2, Cell.Type.WALL), new Cell(5, 3, Cell.Type.WALL),
                new Cell(5, 4, Cell.Type.PASSAGE), new Cell(5, 5, Cell.Type.WALL),
                new Cell(5, 6, Cell.Type.WALL)},
            new Cell[] {new Cell(6, 0, Cell.Type.WALL), new Cell(6, 1, Cell.Type.WALL),
                new Cell(6, 2, Cell.Type.WALL), new Cell(6, 3, Cell.Type.WALL),
                new Cell(6, 4, Cell.Type.WALL), new Cell(6, 5, Cell.Type.WALL),
                new Cell(6, 6, Cell.Type.WALL)}
        };

        String expected = MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            '\n' +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            '\n' +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            '\n' +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            '\n' +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            '\n' +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            BLACK_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            '\n' +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            MAGENTA_BACK + WHITESPACE + RESET +
            '\n';

        // when
        Maze maze = new Maze(SEVEN, SEVEN, grid);
        Renderer renderer = new CIRenderer();

        // then
        assertThat(renderer.render(maze)).isEqualTo(expected);

    }

    @Test
    public void incorrectInput() {

    }
}
