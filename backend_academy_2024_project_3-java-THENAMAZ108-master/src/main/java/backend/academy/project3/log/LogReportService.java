package backend.academy.project3.log;

import backend.academy.project3.clinput.CLInputRecord;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogReportService {
    private static final int TOP = 50;
    private List<LogRecord> logs;
    private CLInputRecord request;

    public LogReport getReport() {
        getLogsWithNeededDate();
        OffsetDateTime startDateTime = request.fromDateTime();
        OffsetDateTime endDateTime = request.toDateTime();
        long totalSize = logs.size();
        int averageBytesSent = getAverageBytesSent();
        var mostFrequentResources = getMostFrequentResources();
        var mostFrequentAnswers = getMostFrequentAnswers();
        var mostFrequentRequests = getMostFrequentRequests();
        long maxRequestsPerDay = getMaxRequestsPerDay() == null ? 0 : getMaxRequestsPerDay();
        var maxRequestsDay = getMaxRequestsDay();
        return new LogReport(
            startDateTime,
            endDateTime,
            totalSize,
            averageBytesSent,
            mostFrequentResources,
            mostFrequentAnswers,
            mostFrequentRequests,
            maxRequestsPerDay,
            maxRequestsDay
        );
    }

    private void getLogsWithNeededDate() {
        OffsetDateTime startDateTime = request.fromDateTime() == null ? OffsetDateTime.MIN : request.fromDateTime();
        OffsetDateTime endDateTime = request.toDateTime() == null ? OffsetDateTime.MAX : request.toDateTime();
        var logList = logs;
        List<LogRecord> newLogList = new ArrayList<>();
        for (var log : logList) {
            if (!log.dateTime().isBefore(startDateTime) && !log.dateTime().isAfter(endDateTime)) {
                newLogList.add(log);
            }
        }
        logs = newLogList;
    }

    private int getAverageBytesSent() {
        return ((int) logs.stream().mapToInt(LogRecord::bodyBytesSent).average().orElse(0));
    }

    private List<Map.Entry<String, Long>> getMostFrequentResources() {
        Map<String, Long> map =
            logs.stream().collect(Collectors.groupingBy(LogRecord::resource, Collectors.counting()));
        return getTop(map);
    }

    private List<Map.Entry<Integer, Long>> getMostFrequentAnswers() {
        Map<Integer, Long> map =
            logs.stream().collect(Collectors.groupingBy(LogRecord::httpStatusCode, Collectors.counting()));
        return getTop(map);
    }

    private List<Map.Entry<String, Long>> getMostFrequentRequests() {
        Map<String, Long> map =
            logs.stream().collect(Collectors.groupingBy(LogRecord::requestType, Collectors.counting()));
        return getTop(map);
    }

    private static <K> List<Map.Entry<K, Long>> getTop(Map<K, Long> map) {
        List<Map.Entry<K, Long>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        List<Map.Entry<K, Long>> finalList = new ArrayList<>();
        for (int i = 0; i < Math.min(list.size(), TOP); ++i) {
            finalList.add(list.get(list.size() - i - 1));
        }
        return finalList;
    }

    private Long getMaxRequestsPerDay() {
        if (groupAndSortByDate().isEmpty()) {
            return null;
        }
        return groupAndSortByDate().getLast().getValue();
    }

    private LocalDate getMaxRequestsDay() {
        if (groupAndSortByDate().isEmpty()) {
            return null;
        }
        return groupAndSortByDate().getLast().getKey();
    }

    private List<Map.Entry<LocalDate, Long>> groupAndSortByDate() {
        var map = logs.stream().collect(Collectors.groupingBy(
            log -> log.dateTime().toLocalDate(),
            Collectors.counting()
        ));
        List<Map.Entry<LocalDate, Long>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return list;
    }
}
