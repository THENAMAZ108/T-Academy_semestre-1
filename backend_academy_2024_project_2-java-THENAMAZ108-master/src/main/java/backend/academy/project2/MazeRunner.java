package backend.academy.project2;

import backend.academy.project2.exception.RoutNotFoundException;
import backend.academy.project2.generator.BFSGenerator;
import backend.academy.project2.generator.Generator;
import backend.academy.project2.generator.KruskalGenerator;
import backend.academy.project2.maze.Coordinate;
import backend.academy.project2.maze.Maze;
import backend.academy.project2.renderer.CIRenderer;
import backend.academy.project2.renderer.Renderer;
import backend.academy.project2.solver.BFSSolver;
import backend.academy.project2.solver.DFSSolver;
import backend.academy.project2.solver.Solver;
import backend.academy.project2.typer.Message;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import lombok.extern.log4j.Log4j2;
import static backend.academy.project2.typer.MessageTyper.typeLikeGPT;

@Log4j2
public class MazeRunner implements Runnable {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final PrintStream PRINTER = System.out;

    private static final String KRUSKAL = "kruskal";
    private static final String BFS = "bfs";
    private static final String DFS = "dfs";

    @Override
    public void run() {
        typeLikeGPT(Message.HELLO, false);

        Generator generator = chooseGenerator();

        Maze maze = generateMaze(generator);

        Renderer renderer = new CIRenderer();

        PRINTER.println(renderer.render(maze) + "\n");

        Solver solver = chooseSolver();

        try {
            List<Coordinate> path = solveMaze(solver, maze);

            PRINTER.println(renderer.render(maze, path) + "\n");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    private List<Coordinate> solveMaze(Solver solver, Maze maze) {
        var start = getCorrectCoordinate(
            maze.height(), maze.width(),
            Message.INPUT_START_X, Message.INPUT_START_Y);

        var end = getCorrectCoordinate(
            maze.height(), maze.width(),
            Message.INPUT_END_X, Message.INPUT_END_Y
        );

        List<Coordinate> path;

        try {
            path = solver.solve(maze, start, end);
        } catch (RoutNotFoundException e) {
            throw new RoutNotFoundException(e.getMessage());
        }
        return path;
    }

    private Coordinate getCorrectCoordinate(int mazeHeight, int mazeWidth, Message messageX, Message messageY) {
        int x = -1;
        int y = -1;
        do {
            try {
                typeLikeGPT(messageX, false);
                x = SCANNER.nextInt();
                SCANNER.nextLine();
                typeLikeGPT(messageY, false);
                y = SCANNER.nextInt();
                SCANNER.nextLine();
            } catch (RuntimeException e) {
                log.error(e.getMessage());
                SCANNER.nextLine();
            }

            if (!isCorrectCoordinate(y, x, mazeHeight, mazeWidth)) {
                typeLikeGPT(String.format(
                    Message.COORDINATES_MUST_BE_BETWEEN.toString(),
                    (mazeHeight - 1) / 2 - 1, (mazeWidth - 1) / 2 - 1), true);
            }

        } while (!isCorrectCoordinate(x, y, mazeHeight, mazeWidth));

        return new Coordinate(x, y);
    }

    private Solver chooseSolver() {
        String inputString;
        do {
            typeLikeGPT(Message.CHOOSE_SOLVER, false);
            inputString = SCANNER.nextLine();
        } while (!DFS.equalsIgnoreCase(inputString) && !BFS.equalsIgnoreCase(inputString));
        if (DFS.equalsIgnoreCase(inputString)) {
            return new DFSSolver();
        }
        return new BFSSolver();
    }

    private Maze generateMaze(Generator generator) {
        int height = 0;
        int width = 0;
        do {
            try {
                typeLikeGPT(Message.INPUT_HEIGHT, false);
                height = SCANNER.nextInt();
                SCANNER.nextLine();
                typeLikeGPT(Message.INPUT_WIDTH, false);
                width = SCANNER.nextInt();
                SCANNER.nextLine();

            } catch (RuntimeException e) {
                log.error(e.getMessage());
                SCANNER.nextLine();
            }

            if (!isCorrectSize(height, width)) {
                typeLikeGPT(Message.WRONG_INPUT_MAZE_SIZE, true);
            }

        } while (!isCorrectSize(height, width));

        return generator.generate(height, width);
    }

    private Generator chooseGenerator() {
        String inputString;
        do {
            typeLikeGPT(Message.CHOOSE_GENERATOR, false);
            inputString = SCANNER.nextLine();
        } while (!KRUSKAL.equalsIgnoreCase(inputString) && !BFS.equalsIgnoreCase(inputString));
        if (KRUSKAL.equalsIgnoreCase(inputString)) {
            return new KruskalGenerator();
        }
        return new BFSGenerator();
    }

    private boolean isCorrectSize(int height, int width) {
        return height > 0 && width > 0;
    }

    private boolean isCorrectCoordinate(int height, int width, int mazeHeight, int mazeWidth) {
        return height >= 0 & width >= 0 & height <= ((mazeHeight - 1) / 2 - 1) & width <= ((mazeWidth - 1) / 2 - 1);
    }
}
