package backend.academy.project3;

import backend.academy.project3.log.LogParser;
import backend.academy.project3.log.LogRecord;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogParserTest {
    @Test
    void testParseValidLog() {
        String log =
            "192.168.1.1 - john_doe [10/Oct/2020:13:55:36 +0000] \"GET /api/resource HTTP/1.1\" 200 532 "
               + "\"http://example.com\" \"Mozilla/5.0\"";
        LogRecord record = LogParser.parse(log);
        assertNotNull(record);
        assertEquals("192.168.1.1", record.remoteAddress());
        assertEquals("john_doe", record.remoteUser());
        assertEquals(OffsetDateTime.parse(
            "10/Oct/2020:13:55:36 +0000",
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH)
        ), record.dateTime());
        assertEquals("GET", record.requestType());
        assertEquals("/api/resource", record.resource());
        assertEquals(200, record.httpStatusCode());
        assertEquals(532, record.bodyBytesSent());
    }

    @Test
    void testParseInvalidLog() {
        String log = "Invalid log format";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> LogParser.parse(log));
        assertEquals("NGINX log was not in correct format: Invalid log format", exception.getMessage());
    }
}
