package backend.academy.project2.generator;

import backend.academy.project2.maze.Cell;
import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import static backend.academy.project2.maze.MazeService.fillWithNodes;
import static backend.academy.project2.maze.MazeService.findNeighboursCoordinates;

/**
 * Класс BFSGenerator реализует алгоритм генерации лабиринта с использованием поиска в ширину (BFS).
 */
public class BFSGenerator implements Generator {
    private static final Random RANDOM = new Random();

    /**
     * Генерирует лабиринт заданного размера.
     *
     * @param height высота лабиринта (в положительных целых числах)
     * @param width ширина лабиринта (в положительных целых числах)
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если высота или ширина неположительные
     */
    @Override
    public Maze generate(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width must be positive");
        }
        Maze maze = fillWithNodes(height, width);
        boolean[][] isVisited = new boolean[height * 2 + 1][width * 2 + 1];
        bfs(maze, isVisited);
        return maze;
    }

    /**
     * Реализует алгоритм поиска в ширину (BFS) для генерации лабиринта.
     *
     * @param maze лабиринт, который будет сгенерирован
     * @param isVisited массив для отслеживания посещенных ячеек
     */
    private void bfs(Maze maze, boolean[][] isVisited) {
        Queue<Coordinate> queue = new LinkedList<>();
        Coordinate start = new Coordinate(1, 1); // Начальная точка генерации лабиринта
        isVisited[start.row()][start.col()] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll(); // Извлечение текущей ячейки из очереди
            var neighbours = findNeighboursCoordinates(current.row(), current.col(), maze.height(), maze.width(),
                isVisited); // Поиск соседей

            if (!neighbours.isEmpty()) {
                queue.add(current); // Добавление текущей ячейки обратно в очередь
                var neighbour = neighbours.get(RANDOM.nextInt(neighbours.size())); // Выбор случайного соседа

                // Создание прохода между текущей ячейкой и соседом
                maze.grid()[(current.row() + neighbour.row()) / 2][(current.col() + neighbour.col()) / 2] = new Cell(
                    (current.row() + neighbour.row()) / 2,
                    (current.col() + neighbour.col()) / 2,
                    Cell.Type.PASSAGE
                );

                isVisited[neighbour.row()][neighbour.col()] = true; // Отметка соседа как посещенного
                queue.add(neighbour); // Добавление соседа в очередь для дальнейшей обработки
            }
        }
    }
}
