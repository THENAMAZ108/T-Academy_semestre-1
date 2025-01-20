package backend.academy.project3.clinput;

import java.time.OffsetDateTime;

public record CLInputRecord(
    String path,
    OffsetDateTime fromDateTime,
    OffsetDateTime toDateTime,
    OutputFormat outputFormat) {
}
