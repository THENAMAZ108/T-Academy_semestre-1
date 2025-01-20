package backend.academy.project3.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DirectoryReader implements Reader {
    private final List<Path> paths;


    /**
     * Reads the content of all files specified by the paths in the list provided
     * in the constructor and returns a list of strings, where each string is a
     * line from one of the files. If a path points to a directory, the method
     * will read all files in that directory recursively.
     *
     * @return a list of strings, where each string is a line from one of the files
     * @throws RuntimeException if an I/O error occurs while reading any of the files
     */
    @Override
    public List<String> read() {
        List<String> strings = new ArrayList<>();
        for (Path path : paths) {
            if (Files.isDirectory(path)) {
                try (Stream<Path> stream = Files.list(path)) {
                    stream.forEach(filePath -> readFile(filePath, strings));
                } catch (IOException e) {
                    throw new RuntimeException(String.format("Error reading directory %s.", path), e);
                }
            } else {
                readFile(path, strings);
            }
        }
        return strings;
    }


    /**
     * Reads the content of a file specified by the given path and adds each line to the provided list of strings.
     *
     * @param path the path to the file to be read
     * @param strings the list to which the lines read from the file will be added
     * @throws RuntimeException if an I/O error occurs while reading the file
     */
    private void readFile(Path path, List<String> strings) {
        try (InputStream inputStream = new FileInputStream(path.toFile());
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            while (bufferedReader.ready()) {
                strings.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error while reading a file by path %s.", path), e);
        }
    }
}
