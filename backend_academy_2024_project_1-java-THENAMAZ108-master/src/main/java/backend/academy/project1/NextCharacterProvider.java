package backend.academy.project1;

public class NextCharacterProvider {
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public NextCharacterProvider(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public char getCharacter() {
        String userInput;
        do {
            userInput = getUserInput();
        } while (!inputIsCorrect(userInput));

        return Character.toLowerCase(userInput.charAt(0));
    }

    private String getUserInput() {
        outputHandler.printMessage(GameMessage.GUESS_NEXT_LETTER);
        return inputHandler.getLine();
    }

    private boolean inputIsCorrect(String userInput) {
        if (userInput == null || userInput.length() != 1) {
            outputHandler.printMessage(GameMessage.ERROR_NOT_A_CHAR);
            return false;
        }

        char character = userInput.charAt(0);

        if (!Character.isLetter(character)) {
            outputHandler.printMessage(GameMessage.ERROR_NOT_A_LETTER);
            return false;
        }
        return true;
    }
}
