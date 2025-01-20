package backend.academy.project3.log;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class LogReport {
    private final OffsetDateTime startDateTime;
    private final OffsetDateTime endDateTime;
    private final long totalSize;
    private final int averageBytesSent;
    private final List<Map.Entry<String, Long>> mostFrequentResources;
    private final List<Map.Entry<Integer, Long>> mostFrequentAnswers;
    private final List<Map.Entry<String, Long>> mostFrequentTypes;
    private final long maxRequestsPerDay;
    private final LocalDate maxRequestsDay;
}
