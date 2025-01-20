package backend.academy.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UnitTestGameSetupTest {

    private UnitTestGameSetup gameSetup;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        gameSetup = new UnitTestGameSetup(new ByteArrayInputStream(new byte[0]), printStream);
    }

    @Test
    void shouldChooseCorrectDifficultyLevel() {
        // Arrange
        String word = "example";
        int expectedDifficulty = word.length() + 2;

        // Act
        int difficulty = gameSetup.chooseDifficulty(word, 1);

        // Assert
        assertThat(difficulty).isEqualTo(expectedDifficulty);
    }

    @Test
    void shouldChooseRandomWordFromSelectedCategory() {
        // Arrange
        Map<String, String> category = Map.of("word1", "description1", "word2", "description2");

        // Act
        String word = gameSetup.localDictionary.getRandomWord(category);

        // Assert
        assertThat(category.keySet()).contains(word);
    }

    @Test
    void shouldPrintErrorMessageOnIncorrectCategoryInput() {
        // Arrange
        int incorrectChoice = -1;

        // Act
        gameSetup.chooseCategory(incorrectChoice);

        // Assert
        assertThat(outputStream.toString()).contains(GameMessage.ERROR_INCORRECT_CATEGORY_INPUT.toString());
    }

    @Test
    void shouldPrintErrorMessageOnIncorrectDifficultyInput() {
        // Arrange
        String word = "example";
        int incorrectChoice = -1;

        // Act
        gameSetup.chooseDifficulty(word, incorrectChoice);

        // Assert
        assertThat(outputStream.toString()).contains(GameMessage.ERROR_INCORRECT_DIFFICULTY_INPUT.toString());
    }
}
