package backend.academy.project2.solver;

import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
