package backend.academy.project3.reader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderFactory {
    private static final Pattern HTTP_PATTERN = Pattern.compile(
        "^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}"
            + "\\b(?:[-a-zA-Z0-9()@:%_+.~#?&/=]*)$");

    public static Reader getReader(String path) {
        if (HTTP_PATTERN.matcher(path).matches()) {
            return getURLReader(path);
        } else {
            return getDirectoryReader(path);
        }
    }

    private static URLReader getURLReader(String path) {
        try {
            return new URLReader(new URI(path));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Incorrect URL.", e);
        }
    }

    private static DirectoryReader getDirectoryReader(String path) {
        try {
            return new DirectoryReader(findFiles(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to find files to read.");
        }
    }

    private static List<Path> findFiles(String pattern) throws IOException {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        List<Path> matchingFiles = new ArrayList<>();

        Files.walkFileTree(Paths.get("."), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                Path relativeFile = Paths.get(".").relativize(file);
                if (matcher.matches(relativeFile)) {
                    matchingFiles.add(relativeFile);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        if (matchingFiles.isEmpty()) {
            throw new IllegalArgumentException("No such file.");
        }
        return matchingFiles;
    }

}
