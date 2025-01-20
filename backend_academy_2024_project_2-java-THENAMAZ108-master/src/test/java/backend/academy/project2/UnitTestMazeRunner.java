package backend.academy.project2;

import backend.academy.project2.generator.BFSGenerator;
import backend.academy.project2.generator.Generator;
import backend.academy.project2.generator.KruskalGenerator;
import backend.academy.project2.solver.BFSSolver;
import backend.academy.project2.solver.DFSSolver;
import backend.academy.project2.solver.Solver;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UnitTestMazeRunner {

    public static final String KRUSKAL = "kruskal";
    public static final String BFS = "bfs";
    public static final String DFS = "dfs";

    public boolean isCorrectSize(int height, int width) {
        return height > 0 && width > 0;
    }

    public boolean isCorrectCoordinate(int height, int width, int mazeHeight, int mazeWidth) {
        return height >= 0 & width >= 0 & height <= ((mazeHeight - 1) / 2 - 1) & width <= ((mazeWidth - 1) / 2 - 1);
    }

    public Generator chooseGenerator(String inputString) {
        if (KRUSKAL.equalsIgnoreCase(inputString)) {
            return new KruskalGenerator();
        } else if (BFS.equalsIgnoreCase(inputString)) {
            return new BFSGenerator();
        }
        return null;
    }

    public Solver chooseSolver(String inputString) {
        if (DFS.equalsIgnoreCase(inputString)) {
            return new DFSSolver();
        } else if (BFS.equalsIgnoreCase(inputString)) {
            return new BFSSolver();
        }
        return null;
    }
}
