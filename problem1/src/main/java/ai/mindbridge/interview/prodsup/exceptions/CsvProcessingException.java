package ai.mindbridge.interview.prodsup.exceptions;

public class CsvProcessingException extends RuntimeException {
    public CsvProcessingException(int rowNumber, String message, Exception cause) {
        super(message + ": line number " + rowNumber, cause);
    }
}
