package backend.academy.project3.writer;

import backend.academy.project3.log.LogReport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import lombok.AllArgsConstructor;
import static backend.academy.project3.writer.WriterUtils.DATE_TIME_FORMATTER;

@AllArgsConstructor
public class MarkDownWriter implements Writer {
    private LogReport logReport;
    private static final String PATH = "log_report.md";
    private static final String TWO_ARGS_LINE = "| %s | %d |\n";
    private static final String THREE_ARGS_LINE = "| %d | %s | %d |\n";
    private static final String HEADER_LINE_TWO = "| %-25s | %-10s |\n";
    private static final String HEADER_LINE_THREE = "| %-5s | %-20s | %-10s |\n";
    private static final String HEADER_DIVIDER_TWO = "|:-------------------------:|------------:|\n";
    private static final String HEADER_DIVIDER_THREE = "|:---:|:---------------------:|-----------:|\n";
    private static final String AMOUNT = "Amount";

    @Override
    public void write() {
        WriterUtils.deleteIfExist(PATH);
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH);
             PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
            printWriter.println(getReport());
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to the file.", e);
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
        stringBuilder.append("#### General Information\n\n");
        stringBuilder.append(String.format(HEADER_LINE_TWO, "Metric", "Value"));
        stringBuilder.append(HEADER_DIVIDER_TWO);
        stringBuilder.append(String.format(
            "| Start Date | %s |\n",
            logReport.startDateTime() == null ? "-"
                : logReport.startDateTime().toLocalDate().format(DATE_TIME_FORMATTER)
        ));
        stringBuilder.append(String.format(
            "| End Date | %s |\n",
            logReport.endDateTime() == null ? "-" : logReport.endDateTime().toLocalDate().format(DATE_TIME_FORMATTER)
        ));
        stringBuilder.append(String.format("| Total Requests | %d |\n", logReport.totalSize()));
        stringBuilder.append(String.format("| Average Bytes Sent | %d |\n", logReport.averageBytesSent()));
        stringBuilder.append(String.format(
            "| Day with Max Requests | %s |\n",
            logReport.maxRequestsDay() == null ? "-" : logReport.maxRequestsDay().format(DATE_TIME_FORMATTER)
        ));
        stringBuilder.append(String.format(
            "| Max Requests per Day | %d |\n",
            logReport.maxRequestsPerDay()
        ));
        return stringBuilder.toString();
    }

    private String getResources() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n#### Requested Resources\n\n");
        stringBuilder.append(String.format(HEADER_LINE_TWO, "Resource", AMOUNT));
        stringBuilder.append(HEADER_DIVIDER_TWO);
        for (Map.Entry<String, Long> resourceAndAmount : logReport.mostFrequentResources()) {
            stringBuilder.append(String.format(
                TWO_ARGS_LINE,
                resourceAndAmount.getKey(),
                resourceAndAmount.getValue()
            ));
        }
        return stringBuilder.toString();
    }

    private String getCodes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n#### Response Codes\n\n");
        stringBuilder.append(String.format(HEADER_LINE_THREE, "Code", "Name", AMOUNT));
        stringBuilder.append(HEADER_DIVIDER_THREE);
        for (Map.Entry<Integer, Long> codeAndAmount : logReport.mostFrequentAnswers()) {
            stringBuilder.append(String.format(
                THREE_ARGS_LINE,
                codeAndAmount.getKey(),
                WriterUtils.getStatusCodes().get(codeAndAmount.getKey()),
                codeAndAmount.getValue()
            ));
        }
        return stringBuilder.toString();
    }

    private String getTypes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n#### Request Types\n\n");
        stringBuilder.append(String.format(HEADER_LINE_TWO, "Type", AMOUNT));
        stringBuilder.append(HEADER_DIVIDER_TWO);
        for (Map.Entry<String, Long> typeAndAmount : logReport.mostFrequentTypes()) {
            stringBuilder.append(String.format(
                TWO_ARGS_LINE,
                typeAndAmount.getKey(),
                typeAndAmount.getValue()
            ));
        }
        return stringBuilder.toString();
    }
}
