package backend.academy.project1;

import java.util.Map;

public class Game {
    private final InputHandler inputHandler = new CLIInputHandler(System.in);
    private final OutputHandler outputHandler = new CLIOutputHandler(System.out);
    private static final int MIN_WORD_LENGTH = 5;

    public void run() {
        outputHandler.printMessage(GameMessage.HELLO);
        GameSetup gameSetup = new GameSetup(System.in, System.out);

        Map.Entry<Integer, Map.Entry<String, String>> gameSettings;
        do {
            gameSettings = gameSetup.setupGame();
        } while (correctWordLength(gameSettings.getValue().getKey()));

        do {
            GameSession gameSession = new GameSession(outputHandler,
                gameSettings.getKey(), gameSettings.getValue().getKey(), gameSettings.getValue().getValue(),
                new NextCharacterProvider(inputHandler, outputHandler)
            );
            gameSession.run();
        } while (userWantsAnotherSession());
    }

    private boolean userWantsAnotherSession() {
        outputHandler.printMessage(GameMessage.LEAVE_GAME);
        String input = inputHandler.getLine();
        return input != null && input.equalsIgnoreCase(GameMessage.YES.toString());
    }

    private boolean correctWordLength(String word) {
        return word.length() < MIN_WORD_LENGTH;
    }
}
