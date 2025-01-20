package backend.academy.project1;

public enum GameMessage {
    HELLO("Hello! This is hangman game!"),
    CHOOSE_CATEGORY("Choose a category: "),
    CHOOSE_DIFFICULTY_LEVEL("Choose a difficulty level: "),
    DIFFICULTY_LEVEL_EASY("1 - Easy"),
    DIFFICULTY_LEVEL_MEDIUM("2 - Medium"),
    DIFFICULTY_LEVEL_HARD("3 - Hard"),
    INPUT_ZERO("Input 0 to choose randomly."),
    ERROR_INCORRECT_CATEGORY_INPUT("Error: incorrect category input. Please input a number from 0 to "),
    ERROR_INCORRECT_DIFFICULTY_INPUT("Error: incorrect difficulty input. Please input a number from 0 to 3"),
    ERROR_NOT_A_NUMBER("Error: not a number. Please input a correct number"),
    ERROR_EMPTY_INPUT("Error: empty input."),
    ERROR_NOT_A_CHAR("Error: not a character. Only one character input is required."),
    ERROR_NOT_A_LETTER("Error: not a letter. Only one letter input is required."),
    GUESS_NEXT_LETTER("Guess the next letter:"),
    ALREADY_USED_LETTER("You already used a letter. Please guess another one."),
    CURRENT_STATE("The word: %s"),
    WIN_SESSION("Congratulations! You WON!"),
    LOSE_SESSION("You LOST!"),
    LETTER_GUESSED_SUCCESSFULLY("Nice! You guessed the letter!"),
    LETTER_NOT_GUESSED("Whoops! Wrong guess!"),
    MISTAKES_LEFT("Mistakes %d of %d"),
    LEAVE_GAME("Do you want to start another session? Type \"да\" if you want or type any other key to end the game."),
    YES("да");

    private final String message;

    GameMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
