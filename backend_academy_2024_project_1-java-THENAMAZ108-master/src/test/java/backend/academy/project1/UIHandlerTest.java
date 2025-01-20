package backend.academy.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class UIHandlerTest {
    private OutputHandler outputHandler;
    private UIHandler uiHandler;

    @BeforeEach
    public void setUp() throws IOException {
        outputHandler = mock(OutputHandler.class);
        uiHandler = new UIHandler(outputHandler);
    }

    @Test
    public void testDisplayGameState_Initial() {
        // Arrange
        String answerState = "_____";
        int attempts = 0;
        int maxAttempts = 5;

        // Act
        uiHandler.displayGameState(answerState, attempts, maxAttempts, null);

        // Assert
        verify(outputHandler).printLine("The word: _____");
        verify(outputHandler).printLine("Mistakes 0 of 5");
    }

    @Test
    public void testDisplayGameState_WithCorrectGuess() {
        // Arrange
        String answerState = "д____";
        int attempts = 0;
        int maxAttempts = 5;

        // Act
        uiHandler.displayGameState(answerState, attempts, maxAttempts, null);

        // Assert
        verify(outputHandler).printLine("The word: д____");
        verify(outputHandler).printLine("Mistakes 0 of 5");
    }

    @Test
    public void testDisplayGameState_WithOneMistake() {
        // Arrange
        String answerState = "д____";
        int attempts = 1;
        int maxAttempts = 5;

        // Act
        uiHandler.displayGameState(answerState, attempts, maxAttempts, null);

        // Assert
        verify(outputHandler).printLine("The word: д____");
        verify(outputHandler).printLine("Mistakes 1 of 5");
    }

    @Test
    public void testDisplayGameState_WithTwoMistakes() {
        // Arrange
        String answerState = "д____";
        int attempts = 2;
        int maxAttempts = 5;

        // Act
        uiHandler.displayGameState(answerState, attempts, maxAttempts, null);

        // Assert
        verify(outputHandler).printLine("The word: д____");
        verify(outputHandler).printLine("Mistakes 2 of 5");
    }

    @Test
    public void testDisplayGameState_WithThreeMistakes() {
        // Arrange
        String answerState = "д____";
        int attempts = 3;
        int maxAttempts = 5;

        // Act
        uiHandler.displayGameState(answerState, attempts, maxAttempts, null);

        // Assert
        verify(outputHandler).printLine("The word: д____");
        verify(outputHandler).printLine("Mistakes 3 of 5");
    }

    @Test
    public void testDisplayGameState_WithFourMistakesAndHint() {
        // Arrange
        String answerState = "д____";
        int attempts = 4;
        int maxAttempts = 5;
        String hint = "Техника прохождения поворотов в управляемом заносе.";

        // Act
        uiHandler.displayGameState(answerState, attempts, maxAttempts, hint);

        // Assert
        verify(outputHandler).printLine("The word: д____");
        verify(outputHandler).printLine("Mistakes 4 of 5");
        verify(outputHandler).printLine("Подсказка: Техника прохождения поворотов в управляемом заносе.");
    }

    @Test
    public void testDisplayGameState_WithFourMistakesAndHint_UpdatedAnswerState() {
        // Arrange
        String answerState = "др___";
        int attempts = 4;
        int maxAttempts = 5;
        String hint = "Техника прохождения поворотов в управляемом заносе.";

        // Act
        uiHandler.displayGameState(answerState, attempts, maxAttempts, hint);

        // Assert
        verify(outputHandler).printLine("The word: др___");
        verify(outputHandler).printLine("Mistakes 4 of 5");
        verify(outputHandler).printLine("Подсказка: Техника прохождения поворотов в управляемом заносе.");
    }

    @Test
    public void testDisplayGameState_WithFiveMistakesAndHint() {
        // Arrange
        String answerState = "др___";
        int attempts = 5;
        int maxAttempts = 5;
        String hint = "Техника прохождения поворотов в управляемом заносе.";

        // Act
        uiHandler.displayGameState(answerState, attempts, maxAttempts, hint);

        // Assert
        verify(outputHandler).printLine("The word: др___");
        verify(outputHandler).printLine("Mistakes 5 of 5");
        verify(outputHandler).printLine("Подсказка: Техника прохождения поворотов в управляемом заносе.");
    }

    @Test
    public void testDisplayHangman_OneMistake() {
        // Arrange
        int attempts = 1;
        int maxAttempts = 5;

        // Act
        uiHandler.displayHangman(attempts, maxAttempts);

        // Assert
        verify(outputHandler).printLine("|\n|\n|\n|");
    }

    @Test
    public void testDisplayHangman_TwoMistakes() {
        // Arrange
        int attempts = 2;
        int maxAttempts = 5;

        // Act
        uiHandler.displayHangman(attempts, maxAttempts);

        // Assert
        verify(outputHandler).printLine("+====+\n|\n|\n|");
    }

    @Test
    public void testDisplayHangman_ThreeMistakes() {
        // Arrange
        int attempts = 3;
        int maxAttempts = 5;

        // Act
        uiHandler.displayHangman(attempts, maxAttempts);

        // Assert
        verify(outputHandler).printLine("+====+\n|    O\n|\n|");
    }

    @Test
    public void testDisplayHangman_FourMistakes() {
        // Arrange
        int attempts = 4;
        int maxAttempts = 5;

        // Act
        uiHandler.displayHangman(attempts, maxAttempts);

        // Assert
        verify(outputHandler).printLine("+====+\n|    O\n|   /|\\\n|");
    }
}
