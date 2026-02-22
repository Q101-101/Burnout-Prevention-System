package bg.sofia.uni.fmi.mjt.burnout.exception;

public class InvalidSubjectRequirementsException extends Exception {

    public InvalidSubjectRequirementsException() {
        this(null);
    }

    public InvalidSubjectRequirementsException(String message) {
        this(message, null);
    }

    public InvalidSubjectRequirementsException(String message, Throwable cause) {
        super(message,  cause);
    }

}
