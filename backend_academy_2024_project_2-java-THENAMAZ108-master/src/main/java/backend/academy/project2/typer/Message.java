package backend.academy.project2.typer;

public enum Message {
    HELLO("Привет, это программа для генерации лабиринтов и поиска путей в них по заданным координатам!"),
    CHOOSE_GENERATOR("Выберите алгоритм генерации лабиринта: напишите \"kruskal\" или \"bfs\": "),
    CHOOSE_SOLVER("Выберите алгоритм генерации лабиринта: напишите \"dfs\" или \"bfs\": "),
    INPUT_HEIGHT("Введите высоту лабиринта: "),
    INPUT_WIDTH("Введите длину лабиринта: "),
    WRONG_INPUT_MAZE_SIZE("Высота и ширина должны быть положительными числами!"),
    INPUT_START_X("Введите стартовую координату по оси X: "),
    INPUT_START_Y("Введите стартовую координату по оси Y: "),
    INPUT_END_X("Введите конечную координату по оси X: "),
    INPUT_END_Y("Введите конечную координату по оси Y: "),
    COORDINATES_MUST_BE_BETWEEN("Координаты должны быть между 0 и %d по X и между 0 и %d по Y");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

