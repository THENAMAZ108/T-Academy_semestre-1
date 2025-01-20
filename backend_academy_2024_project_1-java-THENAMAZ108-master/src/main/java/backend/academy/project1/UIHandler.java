package backend.academy.project1;

import java.io.IOException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class UIHandler {
    private final OutputHandler outputHandler;
    private final Terminal terminal;
    private final LineReader lineReader;

    public UIHandler(OutputHandler outputHandler) throws IOException {
        this.outputHandler = outputHandler;
        this.terminal = TerminalBuilder.terminal();
        this.lineReader = LineReaderBuilder.builder().terminal(terminal).build();
    }

    public void clearScreen() {
        terminal.puts(org.jline.utils.InfoCmp.Capability.clear_screen);
        terminal.flush();
    }

    public void displayGameState(String answerState, int attempts, int maxAttempts, String hint) {
        clearScreen();
        displayHangman(attempts, maxAttempts);
        outputHandler.printLine(String.format(GameMessage.CURRENT_STATE.toString(), answerState));
        outputHandler.printLine(String.format(GameMessage.MISTAKES_LEFT.toString(), attempts, maxAttempts));
        if (hint != null) {
            outputHandler.printLine("Подсказка: " + hint);
        }
    }

    protected void displayHangman(int attempts, int maxAttempts) {
        String[] baseHangmanStages = {
            "|\n|\n|\n|",
            "+====+\n|\n|\n|",
            "+====+\n|    O\n|\n|",
            "+====+\n|    O\n|   /|\\\n|",
            "+====+\n|    O\n|   /|\\\n|   / \\"
        };

        int baseStages = baseHangmanStages.length;
        int extraStages = Math.max(0, maxAttempts - baseStages);
        int remainingAttempts = maxAttempts - attempts;

        // Отрисовка дополнительных кадров (высота столба)
        if (remainingAttempts > baseStages) {
            for (int i = 0; i < attempts; i++) {
                outputHandler.printLine("|");
            }
        } else {
            // Отрисовка базовых кадров
            int stagesToShow = baseStages - remainingAttempts;
            if (stagesToShow > 0) {
                outputHandler.printLine(baseHangmanStages[stagesToShow - 1]);
            }

            // Отрисовка дополнительных кадров (высота столба)
            for (int i = 0; i < extraStages; i++) {
                outputHandler.printLine("|");
            }
        }
    }

}
