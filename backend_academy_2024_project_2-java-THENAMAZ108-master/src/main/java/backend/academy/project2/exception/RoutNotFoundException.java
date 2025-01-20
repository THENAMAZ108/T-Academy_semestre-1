package backend.academy.project2.exception;

public class RoutNotFoundException extends RuntimeException {
    public RoutNotFoundException(String message) {
        super(message);
    }
}
