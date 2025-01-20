package backend.academy.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UnitTestGameSessionTest {
    private OutputHandler outputHandler;
    private NextCharacterProvider nextCharacterProvider;
    private UnitTestGameSession gameSession;

    @BeforeEach
    public void setUp() {
        outputHandler = mock(OutputHandler.class);
        nextCharacterProvider = mock(NextCharacterProvider.class);
        gameSession = new UnitTestGameSession(outputHandler, 5, "спойлер", "Устройство для улучшения аэродинамики автомобиля.", nextCharacterProvider);
    }

    @Test
    public void testIncorrectGuessIncreasesAttempts() {
        // Arrange
        when(nextCharacterProvider.getCharacter()).thenReturn('а');

        // Act
        gameSession.userAnswerHandler.processLetter('а');

        // Assert
        assertEquals(1, gameSession.getAttempts());
        verify(outputHandler).printMessage(GameMessage.LETTER_NOT_GUESSED);
    }

    @Test
    public void testCorrectGuessUpdatesAnswerState() {
        // Arrange
        when(nextCharacterProvider.getCharacter()).thenReturn('с');

        // Act
        gameSession.userAnswerHandler.processLetter('с');

        // Assert
        assertEquals("с______", gameSession.getAnswerState());
        verify(outputHandler).printMessage(GameMessage.LETTER_GUESSED_SUCCESSFULLY);
    }

    @Test
    public void testAlreadyUsedLetterMessage() {
        // Arrange
        when(nextCharacterProvider.getCharacter()).thenReturn('с').thenReturn('с');

        // Act
        gameSession.userAnswerHandler.processLetter('с');
        gameSession.userAnswerHandler.processLetter('с');

        // Assert
        verify(outputHandler).printMessage(GameMessage.ALREADY_USED_LETTER);
    }

    @Test
    public void testAnswerStateEqualsChosenWord() {
        // Arrange
        when(nextCharacterProvider.getCharacter()).thenReturn('с', 'п', 'о', 'й', 'л', 'е', 'р');

        // Act
        gameSession.userAnswerHandler.processLetter('с');
        gameSession.userAnswerHandler.processLetter('п');
        gameSession.userAnswerHandler.processLetter('о');
        gameSession.userAnswerHandler.processLetter('й');
        gameSession.userAnswerHandler.processLetter('л');
        gameSession.userAnswerHandler.processLetter('е');
        gameSession.userAnswerHandler.processLetter('р');

        // Assert
        assertEquals("спойлер", gameSession.getAnswerState());
        //verify(outputHandler).printMessage(GameMessage.WIN_SESSION);
    }

    @Test
    public void testAttemptsEqualsMaxAttempts() {
        // Arrange
        when(nextCharacterProvider.getCharacter()).thenReturn('а', 'б', 'в', 'г', 'д');

        // Act
        gameSession.userAnswerHandler.processLetter('а');
        gameSession.userAnswerHandler.processLetter('б');
        gameSession.userAnswerHandler.processLetter('в');
        gameSession.userAnswerHandler.processLetter('г');
        gameSession.userAnswerHandler.processLetter('д');

        // Assert
        assertEquals(5, gameSession.getAttempts());
        //verify(outputHandler).printMessage(GameMessage.LOSE_SESSION);
    }
}
