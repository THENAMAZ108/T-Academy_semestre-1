package backend.academy.project2.renderer;

import backend.academy.project2.maze.Cell;
import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import java.util.List;

public class CIRenderer implements Renderer {
    private static final String BLACK_BACK = "\33[40m";
    private static final String MAGENTA_BACK = "\33[45m";
    private static final String GREEN_FORE = "\33[0;32m";
    private static final String WHITESPACE = "   ";
    private static final String RESET = "\33[0m";

    @Override
    public String render(Maze maze) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < maze.height(); ++i) {
            for (int j = 0; j < maze.width(); ++j) {
                if (maze.grid()[i][j].type() == Cell.Type.WALL) {
                    stringBuilder.append(MAGENTA_BACK + WHITESPACE + RESET);
                } else {
                    stringBuilder.append(BLACK_BACK + WHITESPACE + RESET);
                }
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();

    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < maze.height(); ++i) {
            for (int j = 0; j < maze.width(); ++j) {
                if (maze.grid()[i][j].type() == Cell.Type.WALL) {
                    stringBuilder.append(MAGENTA_BACK + WHITESPACE + RESET);
                } else if (isPath(i, j, path)) {
                    stringBuilder.append(GREEN_FORE + BLACK_BACK + " @ " + RESET);
                } else {
                    stringBuilder.append(BLACK_BACK + WHITESPACE + RESET);
                }
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    private static boolean isPath(int x, int y, List<Coordinate> path) {
        for (var coord : path) {
            if (coord.row() == x && coord.col() == y) {
                return true;
            }
        }
        return false;
    }
}
