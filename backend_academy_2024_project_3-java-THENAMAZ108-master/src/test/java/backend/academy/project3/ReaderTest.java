package backend.academy.project3;

import backend.academy.project3.reader.DirectoryReader;
import backend.academy.project3.reader.Reader;
import backend.academy.project3.reader.ReaderFactory;
import backend.academy.project3.reader.URLReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReaderTest {
    private static final String FILE_PATH = "src/main/resources/logs_to_analyze/log1.txt";

    @Test
    void testValidHttpPath() {
        // given
        String path =
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

        // when
        Reader reader = ReaderFactory.getReader(path);

        // then
        assertInstanceOf(URLReader.class, reader);
    }

    @Test
    void testGetDirectoryReaderWithValidPath() {
        // given
        String path = "src/**/log1.txt";

        // when
        Reader reader = ReaderFactory.getReader(path);

        // then
        assertInstanceOf(DirectoryReader.class, reader);
        assertEquals(1, ((DirectoryReader) reader).paths().size());
        assertEquals(FILE_PATH, ((DirectoryReader) reader).paths().getFirst().toString());
    }

    @Test
    void testGetDirectoryReaderWithAnotherValidPath() {
        // given
        String path = "src/**/logs_to_analyze/*";

        // when
        Reader reader = ReaderFactory.getReader(path);

        // then
        assertInstanceOf(DirectoryReader.class, reader);
        assertEquals(1, ((DirectoryReader) reader).paths().size());
        assertEquals(FILE_PATH, ((DirectoryReader) reader).paths().getFirst().toString());
    }

    @Test
    void testGetDirectoryReaderWithInvalidPath() {
        // given
        String path = "lolswithkeks/path/*.log";

        // when and then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReaderFactory.getReader(path);
        });
        assertEquals("No such file.", exception.getMessage());
    }

}
