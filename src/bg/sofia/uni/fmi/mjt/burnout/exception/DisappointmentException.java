package bg.sofia.uni.fmi.mjt.burnout.exception;

public class DisappointmentException extends RuntimeException {

    public DisappointmentException() {
        this(null);
    }

    public DisappointmentException(String message) {
        this(message, null);
    }

    public DisappointmentException(String message, Throwable cause) {
        super(message,  cause);
    }

}
