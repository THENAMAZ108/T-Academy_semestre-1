package backend.academy.project1;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class GameSetup {

    private static final int EASY_DIFFICULTY = 1;
    private static final int MEDIUM_DIFFICULTY = 2;
    private static final int HARD_DIFFICULTY = 3;
    private static final int RANDOM_DIFFICULTY = 0;

    private final Dictionary localDictionary;
    private final Scanner scanner;
    private final PrintStream printStream;

    public GameSetup(InputStream inputStream, PrintStream printStream) {
        this.localDictionary = new LocalDictionary();
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public Map.Entry<Integer, Map.Entry<String, String>> setupGame() {
        Map<String, String> selectedCategory = chooseCategory();
        String word = localDictionary.getRandomWord(selectedCategory);
        String hint = selectedCategory.get(word);
        int maxAttempts = chooseDifficulty(word);

        return new AbstractMap.SimpleEntry<>(maxAttempts, new AbstractMap.SimpleEntry<>(word, hint));
    }

    private Map<String, String> chooseCategory() {
        while (true) {
            try {
                printStream.println(GameMessage.CHOOSE_CATEGORY);
                int i = 1;
                for (String categoryName : localDictionary.getCategories().keySet()) {
                    printStream.println(i + " - " + categoryName);
                    i++;
                }
                printStream.println(GameMessage.INPUT_ZERO);
                int choice = scanner.nextInt();

                if (choice < 0 || choice > localDictionary.getCategories().size()) {
                    printStream.println(GameMessage.ERROR_INCORRECT_CATEGORY_INPUT.toString()
                        + localDictionary.getCategories().size());
                } else {
                    return localDictionary.getCategory(choice);
                }
            } catch (InputMismatchException e) {
                handleInputMismatchError();
            } catch (NoSuchElementException e) {
                handleNoSuchElementError();
            }
        }
    }

    private int chooseDifficulty(String word) {
        while (true) {
            try {
                printStream.println(GameMessage.CHOOSE_DIFFICULTY_LEVEL);
                printStream.println(GameMessage.DIFFICULTY_LEVEL_EASY);
                printStream.println(GameMessage.DIFFICULTY_LEVEL_MEDIUM);
                printStream.println(GameMessage.DIFFICULTY_LEVEL_HARD);
                printStream.println(GameMessage.INPUT_ZERO);
                int choice = scanner.nextInt();

                switch (choice) {
                    case EASY_DIFFICULTY:
                        return word.length() + 2;
                    case MEDIUM_DIFFICULTY:
                        return word.length() + 1;
                    case HARD_DIFFICULTY:
                        return word.length();
                    case RANDOM_DIFFICULTY:
                        int[] maxAttempts = {word.length() + 2, word.length() + 1, word.length()};
                        return maxAttempts[new Random().nextInt(maxAttempts.length)];
                    default:
                        printStream.println(GameMessage.ERROR_INCORRECT_DIFFICULTY_INPUT);
                }
            } catch (InputMismatchException e) {
                handleInputMismatchError();
            } catch (NoSuchElementException e) {
                handleNoSuchElementError();
            }
        }
    }

    private void handleInputMismatchError() {
        printStream.println(GameMessage.ERROR_NOT_A_NUMBER);
        scanner.next();
    }

    private void handleNoSuchElementError() {
        printStream.println(GameMessage.ERROR_EMPTY_INPUT);
        scanner.next();
    }
}
