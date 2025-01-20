package backend.academy.project1;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;

public class UnitTestGameSetup {
    private static final int EASY_DIFFICULTY = 1;
    private static final int MEDIUM_DIFFICULTY = 2;
    private static final int HARD_DIFFICULTY = 3;
    private static final int RANDOM_DIFFICULTY = 0;

    protected final Dictionary localDictionary;
    private final PrintStream printStream;

    public UnitTestGameSetup(InputStream inputStream, PrintStream printStream) {
        this.localDictionary = new UnitTestLocalDictionary();
        this.printStream = printStream;
    }

    public AbstractMap.SimpleEntry<Integer, AbstractMap.SimpleEntry<String, String>> setupGame() {
        Map<String, String> selectedCategory = chooseCategory(1); // Пример выбора категории
        String word = localDictionary.getRandomWord(selectedCategory);
        String hint = selectedCategory.get(word);
        int maxAttempts = chooseDifficulty(word, EASY_DIFFICULTY); // Пример выбора уровня сложности

        return new AbstractMap.SimpleEntry<>(maxAttempts, new AbstractMap.SimpleEntry<>(word, hint));
    }

    public Map<String, String> chooseCategory(int choice) {
        if (choice < 0 || choice > localDictionary.getCategories().size()) {
            printStream.println(GameMessage.ERROR_INCORRECT_CATEGORY_INPUT.toString()
                + localDictionary.getCategories().size());
            return null;
        } else {
            return localDictionary.getCategory(choice);
        }
    }

    public int chooseDifficulty(String word, int choice) {
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
                return -1;
        }
    }
}
