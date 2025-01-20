package backend.academy.project3.writer;

import backend.academy.project3.log.LogReport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import lombok.AllArgsConstructor;
import static backend.academy.project3.writer.WriterUtils.DATE_TIME_FORMATTER;

@AllArgsConstructor
public class ADOCWriter implements Writer {
    private LogReport logReport;
    private static final String PATH = "log_report.adoc";
    private static final String TWO_ARGS_LINE = "| %s | %d\n";
    private static final String THREE_ARGS_LINE = "| %d | %s | %d\n";
    private static final String SEPARATOR = "|===\n";
    private static final String HEADER = "[options=\"header\"]\n";

    @Override
    public void write() {
        WriterUtils.deleteIfExist(PATH);
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH);
             PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
            printWriter.println(getReport());
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to the file", e);
        }
    }

    public String getReport() {
        return getGeneralInfo()
            + getResources()
            + getCodes()
            + getTypes();
    }

    private String getGeneralInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==== General Information\n\n");
        stringBuilder.append(HEADER);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("| Metric | Value\n");

        stringBuilder.append(String.format(
            "| Start Date | %s\n",
            logReport.startDateTime() == null
                ? "-" : logReport.startDateTime().toLocalDate().format(DATE_TIME_FORMATTER)
        ));
        stringBuilder.append(String.format(
            "| End Date | %s\n",
            logReport.endDateTime() == null
                ? "-" : logReport.endDateTime().toLocalDate().format(DATE_TIME_FORMATTER)
        ));
        stringBuilder.append(String.format("| Total Requests | %d\n", logReport.totalSize()));
        stringBuilder.append(String.format("| Average Bytes Sent | %d\n", logReport.averageBytesSent()));
        stringBuilder.append(String.format(
            "| Day with Max Requests | %s\n",
            logReport.maxRequestsDay() == null
                ? "-" : logReport.maxRequestsDay().format(DATE_TIME_FORMATTER)
        ));
        stringBuilder.append(String.format(
            "| Max Requests per Day | %d\n",
            logReport.maxRequestsPerDay()
        ));

        stringBuilder.append(SEPARATOR);
        return stringBuilder.toString();
    }

    private String getResources() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==== Requested Resources\n\n");
        stringBuilder.append(HEADER);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("| Resource | Amount\n");

        for (Map.Entry<String, Long> resourceAndAmount : logReport.mostFrequentResources()) {
            stringBuilder.append(String.format(
                TWO_ARGS_LINE,
                resourceAndAmount.getKey(),
                resourceAndAmount.getValue()
            ));
        }

        stringBuilder.append(SEPARATOR);
        return stringBuilder.toString();
    }

    private String getCodes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==== Response Codes\n\n");
        stringBuilder.append(HEADER);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("| Code | Name | Amount\n");

        for (Map.Entry<Integer, Long> codeAndAmount : logReport.mostFrequentAnswers()) {
            stringBuilder.append(String.format(
                THREE_ARGS_LINE,
                codeAndAmount.getKey(),
                WriterUtils.getStatusCodes().get(codeAndAmount.getKey()),
                codeAndAmount.getValue()
            ));
        }

        stringBuilder.append(SEPARATOR);
        return stringBuilder.toString();
    }

    private String getTypes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==== Request Types\n\n");
        stringBuilder.append(HEADER);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("| Type | Amount\n");

        for (Map.Entry<String, Long> typeAndAmount : logReport.mostFrequentTypes()) {
            stringBuilder.append(String.format(
                TWO_ARGS_LINE,
                typeAndAmount.getKey(),
                typeAndAmount.getValue()
            ));
        }

        stringBuilder.append(SEPARATOR);
        return stringBuilder.toString();
    }
}
