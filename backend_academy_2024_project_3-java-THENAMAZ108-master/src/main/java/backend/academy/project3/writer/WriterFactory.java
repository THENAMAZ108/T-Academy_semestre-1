package backend.academy.project3.writer;

import backend.academy.project3.clinput.CLInputRecord;
import backend.academy.project3.log.LogReport;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WriterFactory {
    public static Writer getWriter(CLInputRecord clInputRecord, LogReport logReport) {

        if (clInputRecord.outputFormat() == null) {
            return new TextWriter(logReport);
        }
        return switch (clInputRecord.outputFormat()) {
            case ADOC -> new ADOCWriter(logReport);
            case MARKDOWN -> new MarkDownWriter(logReport);
        };
    }
}
