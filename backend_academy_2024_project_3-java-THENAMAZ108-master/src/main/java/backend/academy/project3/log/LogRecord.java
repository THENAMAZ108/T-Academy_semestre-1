package backend.academy.project3.log;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class LogRecord {
    private final String remoteAddress;
    private final String remoteUser;
    private final OffsetDateTime dateTime;
    private final String requestType;
    private final String resource;
    private final int httpStatusCode;
    private final int bodyBytesSent;
    private final String httpReferrer;
    private final String httpUserAgent;
}
