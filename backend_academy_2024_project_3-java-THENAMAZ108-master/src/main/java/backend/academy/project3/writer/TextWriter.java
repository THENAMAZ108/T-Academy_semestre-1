package backend.academy.project3.writer;

import backend.academy.project3.log.LogReport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import lombok.AllArgsConstructor;
import static backend.academy.project3.writer.WriterUtils.DATE_TIME_FORMATTER;

@AllArgsConstructor
public class TextWriter implements Writer {
    private LogReport logReport;
    private static final String PATH = "log_report.txt";
    private static final String TWO_ARGS_S_D = "%-30s %10d\n";
    private static final String TWO_ARGS_S_S = "%-30s %10s\n";
    private static final String THREE_ARGS_S = "%-15s %-20s %10d\n";
    private static final String THREE_HEADERS_S = "%-15s %-20s %10s\n";
    private static final String AMOUNT = "Amount";

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

    private String getTypes() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n-----Request types:-----\n\n");
        stringBuilder.append(String.format(TWO_ARGS_S_S, "Types", AMOUNT));

        for (Map.Entry<String, Long> typeAndAmount : logReport.mostFrequentTypes()) {
            stringBuilder.append(String.format(TWO_ARGS_S_D, typeAndAmount.getKey(), typeAndAmount.getValue()));
        }

        return stringBuilder.toString();
    }

    private String getCodes() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n-----HTTP codes:-----\n\n");
        stringBuilder.append(String.format(THREE_HEADERS_S, "Code", "Name", AMOUNT));

        for (var codeAndAmount : logReport.mostFrequentAnswers()) {
            stringBuilder.append(String.format(
                THREE_ARGS_S,
                codeAndAmount.getKey(),
                WriterUtils.getStatusCodes().get(codeAndAmount.getKey()),
                codeAndAmount.getValue()
            ));
        }

        return stringBuilder.toString();
    }

    private String getResources() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n-----Resources:-----\n\n");
        stringBuilder.append(String.format(TWO_ARGS_S_S, "Resources", AMOUNT));

        for (Map.Entry<String, Long> resourceAndAmount : logReport.mostFrequentResources()) {
            stringBuilder.append(String.format(TWO_ARGS_S_D, resourceAndAmount.getKey(), resourceAndAmount.getValue()));
        }

        return stringBuilder.toString();
    }

    private String getGeneralInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----General info:-----\n\n");
        stringBuilder.append(String.format(TWO_ARGS_S_S, "Metric", "Value"));

        stringBuilder.append(String.format(TWO_ARGS_S_S, "Start Date",
            logReport.startDateTime() == null
                ? "-" : logReport.startDateTime().toLocalDate().format(DATE_TIME_FORMATTER)));

        stringBuilder.append(String.format(TWO_ARGS_S_S, "End Date",
            logReport.endDateTime() == null
                ? "-" : logReport.endDateTime().toLocalDate().format(DATE_TIME_FORMATTER)));

        stringBuilder.append(String.format(TWO_ARGS_S_D, "Total size", logReport.totalSize()));
        stringBuilder.append(String.format(TWO_ARGS_S_D, "Average bytes sent", logReport.averageBytesSent()));

        stringBuilder.append(String.format(TWO_ARGS_S_S, "Max requests per day",
            logReport.maxRequestsPerDay() == 0
                ? "-" : logReport.maxRequestsPerDay()));

        stringBuilder.append(String.format(TWO_ARGS_S_S, "Day with max requests",
            logReport.maxRequestsDay() == null
                ? "-" : logReport.maxRequestsDay().format(DATE_TIME_FORMATTER)));

        return stringBuilder.toString();
    }
}
