package backend.academy.project3.reader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class URLReader implements Reader {
    private URI uri;
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    @Override
    public List<String> read() {
        HttpRequest httpRequest = getRequest();
        return getResponse(httpRequest);
    }

    private HttpRequest getRequest() {
        return HttpRequest.newBuilder(uri).GET().build();
    }

    private List<String> getResponse(HttpRequest request) {
        try {
            HttpResponse<Stream<String>> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofLines());

            return new ArrayList<>(response.body().toList());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(String.format("Error while reading a file %s.", uri.toString()), e);
        }
    }
}
