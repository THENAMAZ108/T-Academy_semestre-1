package backend.academy.project3.clinput;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CLInputParser {
    private static final Option PATH = Option.builder("p").longOpt("path").hasArg()
        .desc("The path/url to the log file").build();
    private static final Option FROM = Option.builder("f").longOpt("from").hasArg()
        .desc("The start time of the log").build();
    private static final Option TO = Option.builder("t").longOpt("to").hasArg()
        .desc("The end time of the log").build();
    private static final Option OUTPUT_FORMAT = Option.builder("f").longOpt("format").hasArg()
        .desc("The output format").build();

    private static final List<DateTimeFormatter> ISO_FORMATS = List.of(
        DateTimeFormatter.ISO_OFFSET_DATE_TIME,
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    );


    public static CLInputRecord parse(String[] args) {
        CommandLine commandLine;
        try {
            commandLine = new DefaultParser().parse(getOptions(), args);
            if (!commandLine.hasOption(PATH)) {
                throw new RuntimeException("The path/url to the log file is required.");
            }

            var path = commandLine.getOptionValue(PATH);
            var outputFormat = getOutputFormat(commandLine);

            var from = parseDateTime(commandLine, FROM);
            var to = parseDateTime(commandLine, TO);

            return new CLInputRecord(path, from, to, outputFormat);

        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse the command line.", e);
        }
    }


    /**
     * Creates an instance of {@link Options} containing the command line options recognized by this parser.
     *
     * @return the {@link Options} instance
     */
    private static Options getOptions() {
        Options options = new Options();
        options.addOption(PATH);
        options.addOption(FROM);
        options.addOption(TO);
        options.addOption(OUTPUT_FORMAT);
        return options;
    }


    private static OffsetDateTime parseDateTime(CommandLine commandLine, Option option) {
        if (!commandLine.hasOption(option)) {
            return null;
        }
        var dateTimeStr = commandLine.getOptionValue(option);

        for (DateTimeFormatter formatter : ISO_FORMATS) {
            try {
                if (formatter == DateTimeFormatter.ISO_LOCAL_DATE) {
                    return OffsetDateTime.of(
                        LocalDate.parse(dateTimeStr, formatter),
                        LocalTime.MIDNIGHT,
                        ZoneOffset.UTC
                    );

                } else if (formatter == DateTimeFormatter.ISO_LOCAL_DATE_TIME) {
                    return OffsetDateTime.of(
                        LocalDateTime.parse(dateTimeStr, formatter),
                        ZoneOffset.UTC
                    );

                } else {
                    return OffsetDateTime.parse(dateTimeStr, formatter);
                }
            } catch (DateTimeParseException ignored) {
                // Игнорируем и пробуем использовать следующий формат
            }
        }
        throw new IllegalArgumentException("Invalid date/time format: " + dateTimeStr);
    }


    private static OutputFormat getOutputFormat(CommandLine commandLine) {
        if (!commandLine.hasOption(OUTPUT_FORMAT)) {
            return null;
        }
        var formatString = commandLine.getOptionValue(OUTPUT_FORMAT);
        switch (formatString) {
            case "markdown" -> {
                return OutputFormat.MARKDOWN;
            }
            case "adoc" -> {
                return OutputFormat.ADOC;
            }
            default -> throw new IllegalArgumentException("Incorrect file format: " + formatString);
        }
    }
}
