package backend.academy.project3;

import backend.academy.project3.clinput.CLInputParser;
import backend.academy.project3.clinput.CLInputRecord;
import backend.academy.project3.log.LogParser;
import backend.academy.project3.log.LogReport;
import backend.academy.project3.log.LogReportService;
import backend.academy.project3.reader.Reader;
import backend.academy.project3.reader.ReaderFactory;
import backend.academy.project3.writer.Writer;
import backend.academy.project3.writer.WriterFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Main {
    public static void main(String[] args) {
        CLInputRecord clInputRecord = CLInputParser.parse(args);
        Reader reader = ReaderFactory.getReader(clInputRecord.path());
        var logRecordStream = reader.read().stream().map(LogParser::parse).toList();
        LogReport logReport = new LogReportService(logRecordStream, clInputRecord).getReport();
        Writer writer = WriterFactory.getWriter(clInputRecord, logReport);
        writer.write();
    }
}
