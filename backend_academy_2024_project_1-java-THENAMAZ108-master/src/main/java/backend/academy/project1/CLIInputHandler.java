package backend.academy.project1;

import java.io.InputStream;
import java.util.Scanner;

public class CLIInputHandler implements InputHandler {
    private final Scanner scanner;

    public CLIInputHandler(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String getLine() {
        return scanner.nextLine();
    }
}
