package backend.academy.project2.renderer;

import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
