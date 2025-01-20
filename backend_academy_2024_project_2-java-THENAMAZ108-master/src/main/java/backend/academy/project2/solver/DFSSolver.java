package backend.academy.project2.solver;

import backend.academy.project2.exception.RoutNotFoundException;
import backend.academy.project2.maze.Cell;
import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import static backend.academy.project2.maze.MazeService.findNeighboursCoordinates;

/**
 * Класс DFSSolver реализует алгоритм поиска пути в лабиринте с использованием поиска в глубину (DFS).
 */
public class DFSSolver implements Solver {

    /**
     * Реализует поиск пути в лабиринте от начальной до конечной координаты.
     *
     * @param maze лабиринт
     * @param start начальная координата
     * @param end конечная координата
     * @return путь в виде списка координат
     * @throws RoutNotFoundException если путь не найден
     */
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        Coordinate newStart = new Coordinate(start.row() * 2 + 1, start.col() * 2 + 1);
        Coordinate newEnd = new Coordinate(end.row() * 2 + 1, end.col() * 2 + 1);
        boolean[][] isVisited = new boolean[maze.height()][maze.width()];
        List<Coordinate> path = new ArrayList<>();
        if (!dfs(newStart, newEnd, maze, isVisited, path)) {
            throw new RoutNotFoundException("No way was found");
        }
        path.add(newStart);
        return path;
    }

    /**
     * Реализует алгоритм поиска в глубину (DFS) для нахождения пути.
     *
     * @param start начальная координата
     * @param end конечная координата
     * @param maze лабиринт
     * @param isVisited массив для отслеживания посещенных ячеек
     * @param path список координат для хранения пути
     * @return true, если путь найден; иначе false
     */
    private boolean dfs(Coordinate start, Coordinate end, Maze maze, boolean[][] isVisited, List<Coordinate> path) {
        isVisited[start.row()][start.col()] = true;
        if (start.col() == end.col() && start.row() == end.row()) {
            return true;
        }
        var neighboursCoords = findNeighboursCoordinates(start.row(), start.col(), maze.height(), maze.width(),
            isVisited);
        for (var neighbour : neighboursCoords) {
            if (isVisited[neighbour.row()][neighbour.col()]) {
                continue;
            }
            Coordinate betweenCoordinate = new Coordinate(
                (start.row() + neighbour.row()) / 2,
                (start.col() + neighbour.col()) / 2
            );
            if (maze.grid()[betweenCoordinate.row()][betweenCoordinate.col()].type() == Cell.Type.WALL) {
                continue;
            }
            if (dfs(neighbour, end, maze, isVisited, path)) {
                path.add(betweenCoordinate);
                path.add(neighbour);
                return true;
            }
        }
        return false;
    }
}
