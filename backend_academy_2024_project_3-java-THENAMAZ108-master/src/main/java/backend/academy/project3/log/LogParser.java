package backend.academy.project3.log;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogParser {
    private static final Pattern NGINX_REGEX =
        Pattern.compile(
            "(\\S*) - (\\S*) \\[(\\d{2}/[A-Z][a-z]+/\\d{4}(?::\\d{2}){3} [+-]\\d{4})] \"([A-Z]+) "
               + "(\\S*) \\S*\" (\\d{3}) (\\d+) \"(.*)\" \"(.*)\"");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    private static final int ADDRESS = 1;
    private static final int USER = 2;
    private static final int DATE = 3;
    private static final int TYPE = 4;
    private static final int RESOURCE = 5;
    private static final int STATUS = 6;
    private static final int SIZE = 7;
    private static final int REFERER = 8;
    private static final int AGENT = 9;

    public static LogRecord parse(String logLine) {
        Matcher matcher = NGINX_REGEX.matcher(logLine);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("NGINX log was not in correct format: %s", logLine));
        }

        return new LogRecord(
            matcher.group(ADDRESS),
            matcher.group(USER),
            OffsetDateTime.parse(matcher.group(DATE), DATE_TIME_FORMATTER),
            matcher.group(TYPE),
            matcher.group(RESOURCE),
            Integer.parseInt(matcher.group(STATUS)),
            Integer.parseInt(matcher.group(SIZE)),
            matcher.group(REFERER),
            matcher.group(AGENT)
        );
    }
}
