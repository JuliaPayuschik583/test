package exception;

/**
 * @author Julia
 */
public class ParserException extends Exception {
    private String message;

    public ParserException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ParserException{" +
                "message='" + message + '\'' +
                '}';
    }
}
