package backend.academy.project2.generator;

import backend.academy.project2.maze.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
