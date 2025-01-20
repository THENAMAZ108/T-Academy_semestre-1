package backend.academy.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Queue;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UnitTestNextCharacterProviderTest {

    private UnitTestNextCharacterProvider characterProvider;
    private InputHandler mockInputHandler;
    private OutputHandler mockOutputHandler;
    private Queue<String> inputQueue;

    @BeforeEach
    void setUp() {
        mockInputHandler = Mockito.mock(InputHandler.class);
        mockOutputHandler = Mockito.mock(OutputHandler.class);
        inputQueue = new LinkedList<>();
        characterProvider = new UnitTestNextCharacterProvider(mockInputHandler, mockOutputHandler);
    }

    @Test
    void shouldReturnCorrectCharacter() {
        // Arrange
        inputQueue.add("A");
        when(mockInputHandler.getLine()).thenAnswer(_ -> inputQueue.poll());

        // Act
        char character = characterProvider.getCharacter();

        // Assert
        assertThat(character).isEqualTo('a');
    }

    @Test
    void shouldRepeatInputOnInvalidLength() {
        // Arrange
        inputQueue.add("ab");
        inputQueue.add("a");
        when(mockInputHandler.getLine()).thenAnswer(_ -> inputQueue.poll());

        // Act
        char character = characterProvider.getCharacter();

        // Assert
        assertThat(character).isEqualTo('a');
        verify(mockOutputHandler, times(1)).printMessage(GameMessage.ERROR_NOT_A_CHAR);
    }

    @Test
    void shouldRepeatInputOnNonLetterCharacter() {
        // Arrange
        inputQueue.add("1");
        inputQueue.add("a");
        when(mockInputHandler.getLine()).thenAnswer(_ -> inputQueue.poll());

        // Act
        char character = characterProvider.getCharacter();

        // Assert
        assertThat(character).isEqualTo('a');
        verify(mockOutputHandler, times(1)).printMessage(GameMessage.ERROR_NOT_A_LETTER);
    }

    @Test
    void shouldConvertCharacterToLowerCase() {
        // Arrange
        inputQueue.add("A");
        when(mockInputHandler.getLine()).thenAnswer(_ -> inputQueue.poll());

        // Act
        char character = characterProvider.getCharacter();

        // Assert
        assertThat(character).isEqualTo('a');
    }
}
