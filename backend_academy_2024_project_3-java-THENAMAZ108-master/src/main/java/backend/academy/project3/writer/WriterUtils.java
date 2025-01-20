package backend.academy.project3.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("MagicNumber")
public class WriterUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE;

    public static Map<Integer, String> getStatusCodes() {
        return LazyHolder.INSTANCE;
    }

    public static void deleteIfExist(String path) {
        Path path1 = Path.of(path);
        if (Files.exists(path1)) {
            try {
                Files.delete(path1);
            } catch (IOException e) {
                throw new RuntimeException("Failed delete existing file.", e);
            }
        }
    }

    private static Map<Integer, String> createMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(100, "Continue");
        map.put(101, "Switching Protocols");
        map.put(102, "Processing");
        map.put(200, "OK");
        map.put(201, "Created");
        map.put(202, "Accepted");
        map.put(203, "Non-Authoritative Information");
        map.put(204, "No Content");
        map.put(205, "Reset Content");
        map.put(206, "Partial Content");
        map.put(207, "Multi-Status");
        map.put(208, "Already Reported");
        map.put(226, "IM Used");

        map.put(300, "Multiple Choices");
        map.put(301, "Moved Permanently");
        map.put(302, "Found");
        map.put(303, "See Other");
        map.put(304, "Not Modified");
        map.put(305, "Use Proxy");
        map.put(307, "Temporary Redirect");
        map.put(308, "Permanent Redirect");

        map.put(400, "Bad Request");
        map.put(401, "Unauthorized");
        map.put(402, "Payment Required");
        map.put(403, "Forbidden");
        map.put(404, "Not Found");
        map.put(405, "Method Not Allowed");
        map.put(406, "Not Acceptable");
        map.put(407, "Proxy Authentication Required");
        map.put(408, "Request Timeout");
        map.put(409, "Conflict");
        map.put(410, "Gone");
        map.put(411, "Length Required");
        map.put(412, "Precondition Failed");
        map.put(413, "Payload Too Large");
        map.put(414, "URI Too Long");
        map.put(415, "Unsupported Media Type");
        map.put(416, "Range Not Satisfiable");
        map.put(417, "Expectation Failed");
        map.put(418, "I'm a teapot");
        map.put(421, "Misdirected Request");
        map.put(422, "Unprocessable Entity");
        map.put(423, "Locked");
        map.put(424, "Failed Dependency");
        map.put(425, "Too Early");
        map.put(426, "Upgrade Required");
        map.put(428, "Precondition Required");
        map.put(429, "Too Many Requests");
        map.put(431, "Request Header Fields Too Large");
        map.put(451, "Unavailable For Legal Reasons");

        map.put(500, "Internal Server Error");
        map.put(501, "Not Implemented");
        map.put(502, "Bad Gateway");
        map.put(503, "Service Unavailable");
        map.put(504, "Gateway Timeout");
        map.put(505, "HTTP Version Not Supported");
        map.put(506, "Variant Also Negotiates");
        map.put(507, "Insufficient Storage");
        map.put(508, "Loop Detected");
        map.put(510, "Not Extended");
        map.put(511, "Network Authentication Required");

        return Collections.unmodifiableMap(map);
    }

    private static class LazyHolder {

        static final Map<Integer, String> INSTANCE = createMap();
    }
}
