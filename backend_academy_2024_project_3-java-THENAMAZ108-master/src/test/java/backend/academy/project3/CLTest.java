package backend.academy.project3;

import backend.academy.project3.clinput.CLInputParser;
import backend.academy.project3.clinput.CLInputRecord;
import backend.academy.project3.clinput.OutputFormat;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CLTest {
    @Test
    void testValidInputWithVariousFormats() {
        // given
        String[] argsDateOnly =
            {"--path", "logs/*.log", "--from", "2023-01-01", "--to", "2023-01-31", "--format", "markdown"};

        // when
        CLInputRecord resultDateOnly = CLInputParser.parse(argsDateOnly);

        // then
        assertNotNull(resultDateOnly);
        assertEquals("logs/*.log", resultDateOnly.path());
        assertEquals(OffsetDateTime.parse("2023-01-01T00:00Z"), resultDateOnly.fromDateTime());
        assertEquals(OffsetDateTime.parse("2023-01-31T00:00Z"), resultDateOnly.toDateTime());
        assertEquals(OutputFormat.MARKDOWN, resultDateOnly.outputFormat());

        // given
        String[] argsDateTimeWithZone =
            {"--path", "logs/*.log", "--from", "2023-01-01T10:15:30+02:00", "--to", "2023-01-31T20:45:15-05:00",
                "--format", "markdown"};

        // when
        CLInputRecord resultDateTimeWithZone = CLInputParser.parse(argsDateTimeWithZone);

        // then
        assertNotNull(resultDateTimeWithZone);
        assertEquals("logs/*.log", resultDateTimeWithZone.path());
        assertEquals(OffsetDateTime.parse("2023-01-01T10:15:30+02:00"), resultDateTimeWithZone.fromDateTime());
        assertEquals(OffsetDateTime.parse("2023-01-31T20:45:15-05:00"), resultDateTimeWithZone.toDateTime());
        assertEquals(OutputFormat.MARKDOWN, resultDateTimeWithZone.outputFormat());

        // given
        String[] argsDateTimeNoZone =
            {"--path", "logs/*.log", "--from", "2023-01-01T10:15:30", "--to", "2023-01-31T20:45:15", "--format",
                "markdown"};

        // when
        CLInputRecord resultDateTimeNoZone = CLInputParser.parse(argsDateTimeNoZone);

        // then
        assertNotNull(resultDateTimeNoZone);
        assertEquals("logs/*.log", resultDateTimeNoZone.path());
        assertEquals(OffsetDateTime.parse("2023-01-01T10:15:30Z"), resultDateTimeNoZone.fromDateTime());
        assertEquals(OffsetDateTime.parse("2023-01-31T20:45:15Z"), resultDateTimeNoZone.toDateTime());
        assertEquals(OutputFormat.MARKDOWN, resultDateTimeNoZone.outputFormat());
    }

    @Test
    void testMissingPath() {
        // given
        String[] args = {"--from", "2023-01-01", "--to", "2023-01-31"};

        // when and then
        Exception exception = assertThrows(RuntimeException.class, () -> CLInputParser.parse(args));
        assertEquals("The path/url to the log file is required.", exception.getMessage());
    }

    @Test
    void testInvalidFormat() {
        // given
        String[] args = {"--path", "logs/*.log", "--format", "invalidFormat"};

        // when and then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CLInputParser.parse(args));
        assertEquals("Incorrect file format: invalidFormat", exception.getMessage());
    }
}
