package backend.academy.project2.maze;

import java.util.ArrayList;
import java.util.List;

public class MazeService {
    private static final int[] ROW_SHIFTER = new int[] {2, 0, -2, 0};
    private static final int[] COL_SHIFTER = new int[] {0, 2, 0, -2};

    private MazeService() {
    }

    public static Maze fillWithNodes(int height, int width) {
        Maze maze = new Maze(height * 2 + 1, width * 2 + 1);
        for (int i = 0; i < maze.height(); ++i) {
            for (int j = 0; j < maze.width(); ++j) {
                if (i % 2 != 0 && j % 2 != 0) {
                    maze.grid()[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                } else {
                    maze.grid()[i][j] = new Cell(i, j, Cell.Type.WALL);
                }
            }
        }
        return maze;
    }

    public static List<Coordinate> findNeighboursCoordinates(
        int cellRow, int cellCol,
        int mazeRows, int mazeCols,
        boolean[][] isVisited
    ) {
        List<Coordinate> newCellsCoords = new ArrayList<>();
        for (int i = 0; i < ROW_SHIFTER.length; ++i) {
            int neighbourCellRow = cellRow + ROW_SHIFTER[i];
            int neighbourCellCol = cellCol + COL_SHIFTER[i];
            if (neighbourCellRow >= 0
                && neighbourCellRow < mazeRows
                && neighbourCellCol >= 0
                && neighbourCellCol < mazeCols
                && !isVisited[neighbourCellRow][neighbourCellCol]) {
                newCellsCoords.add(new Coordinate(neighbourCellRow, neighbourCellCol));
            }
        }
        return newCellsCoords;
    }
}
