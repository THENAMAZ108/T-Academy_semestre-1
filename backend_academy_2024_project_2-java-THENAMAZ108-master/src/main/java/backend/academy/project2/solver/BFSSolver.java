package backend.academy.project2.solver;

import backend.academy.project2.exception.RoutNotFoundException;
import backend.academy.project2.maze.Cell;
import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import static backend.academy.project2.maze.MazeService.findNeighboursCoordinates;

/**
 * Класс BFSSolver реализует алгоритм поиска пути в лабиринте с использованием поиска в ширину (BFS).
 */
public class BFSSolver implements Solver {
    private static final Random RANDOM = new Random();

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
        var previous = bfs(maze, isVisited, newStart);
        List<Coordinate> path;
        try {
            path = restorePath(previous, newEnd);
        } catch (RoutNotFoundException e) {
            throw new RoutNotFoundException(e.getMessage());
        }
        return path;
    }

    /**
     * Реализует алгоритм поиска в ширину (BFS) для нахождения пути.
     *
     * @param maze лабиринт
     * @param isVisited массив для отслеживания посещенных ячеек
     * @param start начальная координата
     * @return массив предыдущих координат для каждой ячейки
     */
    private Coordinate[][] bfs(Maze maze, boolean[][] isVisited, Coordinate start) {
        Queue<Coordinate> queue = new LinkedList<>();
        isVisited[start.row()][start.col()] = true;
        Coordinate[][] previous = new Coordinate[maze.height()][maze.width()];
        previous[start.row()][start.col()] = new Coordinate(-1, -1);
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            var neighbours = findNeighboursCoordinates(
                current.row(), current.col(),
                maze.height(), maze.width(),
                isVisited
            );
            List<Coordinate> trueNeighbours = new ArrayList<>();
            for (var neighbour : neighbours) {
                Coordinate betweenCoordinate = new Coordinate(
                    (current.row() + neighbour.row()) / 2,
                    (current.col() + neighbour.col()) / 2
                );
                if (maze.grid()[betweenCoordinate.row()][betweenCoordinate.col()].type() != Cell.Type.WALL) {
                    trueNeighbours.add(neighbour);
                }
            }
            neighbours = trueNeighbours;
            if (!neighbours.isEmpty()) {
                queue.add(current);
                int index = RANDOM.nextInt(neighbours.size());
                var neighbour = neighbours.get(index);
                isVisited[neighbour.row()][neighbour.col()] = true;
                queue.add(neighbour);
                previous[neighbour.row()][neighbour.col()] = current;
            }
        }
        return previous;
    }

    /**
     * Восстанавливает путь от конечной координаты до начальной.
     *
     * @param previous массив предыдущих координат для каждой ячейки
     * @param end конечная координата
     * @return путь в виде списка координат
     * @throws RoutNotFoundException если путь не найден
     */
    private List<Coordinate> restorePath(Coordinate[][] previous, Coordinate end) {
        if (previous[end.row()][end.col()] == null) {
            throw new RoutNotFoundException("No way was found");
        }
        List<Coordinate> path = new ArrayList<>();
        Coordinate cur = end;
        while (cur.row() != -1) {
            path.add(cur);
            var prev = previous[cur.row()][cur.col()];
            path.add(new Coordinate((cur.row() + prev.row()) / 2, (cur.col() + prev.col()) / 2));
            cur = prev;
        }
        return path;
    }
}
