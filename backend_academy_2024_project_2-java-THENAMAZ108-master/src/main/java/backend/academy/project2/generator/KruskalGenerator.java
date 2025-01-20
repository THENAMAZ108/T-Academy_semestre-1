package backend.academy.project2.generator;

import backend.academy.project2.maze.Cell;
import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static backend.academy.project2.maze.MazeService.fillWithNodes;

/**
 * Класс KruskalGenerator реализует алгоритм генерации лабиринта с использованием алгоритма Крускала.
 */
public class KruskalGenerator implements Generator {

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
        int mazeRows = height * 2 + 1;
        int mazeCols = width * 2 + 1;
        DisjointSet ds = new DisjointSet(mazeRows * mazeCols);
        List<Edge> edges = getAllEdges(maze);
        Collections.shuffle(edges); // Перемешиваем рёбра для рандомного выбора

        // Основной цикл алгоритма Крускала
        for (Edge edge : edges) {
            int set1 = ds.find(edge.cell1);
            int set2 = ds.find(edge.cell2);
            if (set1 != set2) {
                ds.union(set1, set2); // Объединение множеств
                createPassage(maze, edge); // Создание прохода между клетками
            }
        }
        return maze;
    }

    /**
     * Получает все возможные рёбра (edges) для лабиринта.
     *
     * @param maze лабиринт
     * @return список рёбер (edges)
     */
    private List<Edge> getAllEdges(Maze maze) {
        List<Edge> edges = new ArrayList<>();
        int height = maze.height();
        int width = maze.width();

        // Проходим по всем клеткам лабиринта и добавляем рёбра
        for (int row = 1; row < height; row += 2) {
            for (int col = 1; col < width; col += 2) {
                if (row + 2 < height) {
                    edges.add(new Edge(toIndex(row, col, width),
                        toIndex(row + 2, col, width),
                        new Coordinate(row + 1, col)));
                }
                if (col + 2 < width) {
                    edges.add(new Edge(toIndex(row, col, width),
                        toIndex(row, col + 2, width),
                        new Coordinate(row, col + 1)));
                }
            }
        }
        return edges;
    }

    /**
     * Преобразует координаты клетки в индекс.
     *
     * @param row строка клетки
     * @param col столбец клетки
     * @param width ширина лабиринта
     * @return индекс клетки
     */
    private int toIndex(int row, int col, int width) {
        return row * width + col;
    }

    /**
     * Создаёт проход между двумя клетками.
     *
     * @param maze лабиринт
     * @param edge ребро, представляющее две клетки
     */
    private void createPassage(Maze maze, Edge edge) {
        Coordinate passage = edge.passage;
        maze.grid()[passage.row()][passage.col()] = new Cell(passage.row(), passage.col(), Cell.Type.PASSAGE);
    }

    /**
     * Вспомогательный класс для представления рёбер.
     */
    private static class Edge {
        int cell1;
        int cell2;
        Coordinate passage;

        Edge(int cell1, int cell2, Coordinate passage) {
            this.cell1 = cell1;
            this.cell2 = cell2;
            this.passage = passage;
        }
    }

    /**
     * Класс для представления объединённого множества (Disjoint Set).
     */
    private static class DisjointSet {
        int[] parent;
        int[] rank;

        DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        /**
         * Находит корневой элемент множества, к которому принадлежит элемент i.
         *
         * @param i элемент
         * @return корневой элемент множества
         */
        int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        /**
         * Объединяет два множества, к которым принадлежат элементы i и j.
         *
         * @param i элемент множества
         * @param j элемент множества
         */
        void union(int i, int j) {
            int root1 = find(i);
            int root2 = find(j);
            if (root1 != root2) {
                if (rank[root1] > rank[root2]) {
                    parent[root2] = root1;
                } else if (rank[root1] < rank[root2]) {
                    parent[root1] = root2;
                } else {
                    parent[root2] = root1;
                    rank[root1]++;
                }
            }
        }
    }
}
