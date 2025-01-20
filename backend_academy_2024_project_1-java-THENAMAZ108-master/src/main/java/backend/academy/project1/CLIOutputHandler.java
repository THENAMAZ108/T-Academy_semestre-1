package backend.academy.project1;

import java.io.PrintStream;

public class CLIOutputHandler implements OutputHandler {
    private final PrintStream printStream;

    public CLIOutputHandler(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void printLine(String line) {
        printStream.println(line);
    }

    @Override
    public void printMessage(GameMessage message) {
        printStream.println(message.toString());
    }
}
