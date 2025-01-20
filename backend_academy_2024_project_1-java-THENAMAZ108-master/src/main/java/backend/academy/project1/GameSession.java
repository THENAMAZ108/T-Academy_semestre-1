package backend.academy.project1;

import java.io.IOException;
import java.util.HashSet;

public class GameSession {
    private final OutputHandler outputHandler;

    private final String chosenWord;
    private final String hint;
    private boolean displayHint = false;
    private final HashSet<Character> userAnswers = new HashSet<>();

    private final int maxAttempts;
    private int attempts = 0;

    private final NextCharacterProvider nextCharacterProvider;
    private final UserAnswerHandler userAnswerHandler;

    private final UIHandler uiHandler;
    private static final int NUMBER_OF_EMPTY_LINES = 30;
    String screenCleaner = "\n".repeat(NUMBER_OF_EMPTY_LINES);


    public GameSession(OutputHandler outputHandler,
        int maxAttempts, String chosenWord, String hint,
        NextCharacterProvider nextCharacterProvider
    ) {
        this.outputHandler = outputHandler;
        this.chosenWord = chosenWord;
        this.maxAttempts = maxAttempts;
        this.nextCharacterProvider = nextCharacterProvider;
        this.userAnswerHandler = new UserAnswerHandler();
        this.hint = hint;

        try {
            this.uiHandler = new UIHandler(outputHandler);
        } catch (IOException e) {
            outputHandler.printLine("Ошибка инициализации UIHandler: " + e.getMessage());
            System.exit(1); // Завершение работы приложения с кодом ошибки 1
            throw new RuntimeException(e);
        }
    }

    public void run() {
        do {
            uiHandler.displayGameState(userAnswerHandler.getAnswerState(), attempts, maxAttempts, getHint());
            char letter = nextCharacterProvider.getCharacter();
            userAnswerHandler.processLetter(letter);
        } while (shouldStartNextRound());
    }

    private boolean shouldStartNextRound() {
        if (attempts == maxAttempts) {
            loseSession();
            return false;
        } else if (userAnswerHandler.getAnswerState().equals(chosenWord)) {
            winSession();
            return false;
        }
        return true;
    }

    private void winSession() {
        outputHandler.printMessage(GameMessage.WIN_SESSION);
    }

    private void loseSession() {
        outputHandler.printMessage(GameMessage.LOSE_SESSION);
    }

    private String getHint() {
        return displayHint ? hint : null;
    }


    private class UserAnswerHandler {
        private String answerState;

        {
            updateAnswerState();
        }

        public String getAnswerState() {
            return answerState;
        }

        private void updateAnswerState() {
            StringBuilder newAnswerState = new StringBuilder();
            for (int i = 0; i < chosenWord.length(); ++i) {
                char letter = chosenWord.charAt(i);
                newAnswerState.append(userAnswers.contains(chosenWord.charAt(i)) ? letter : '_');
            }

            answerState = newAnswerState.toString();
        }

        private void processLetter(char letter) {
            if (userAnswers.contains(letter)) {
                alreadyUsedLetter();
                return;
            }
            processGuess(letter);
        }

        private void alreadyUsedLetter() {
            outputHandler.printLine(screenCleaner);
            outputHandler.printMessage(GameMessage.ALREADY_USED_LETTER);
        }

        private void processGuess(char letter) {
            userAnswers.add(letter);

            if (chosenWord.contains(Character.toString(letter))) {
                letterGuessedSuccessfully();
            } else {
                letterNotGuessed();
            }
        }

        private void letterGuessedSuccessfully() {
            outputHandler.printLine(screenCleaner);
            outputHandler.printMessage(GameMessage.LETTER_GUESSED_SUCCESSFULLY);
            updateAnswerState();
        }

        private void letterNotGuessed() {
            ++attempts;

            if (maxAttempts - attempts == 1) {
                displayHint = true;
            }

            outputHandler.printLine(screenCleaner);
            outputHandler.printMessage(GameMessage.LETTER_NOT_GUESSED);
        }
    }
}
