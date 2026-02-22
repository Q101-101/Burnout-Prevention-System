package bg.sofia.uni.fmi.mjt.burnout.exception;

public class CryToStudentsDepartmentException extends RuntimeException {

    public CryToStudentsDepartmentException() {
        this(null);
    }

    public CryToStudentsDepartmentException(String message) {
        this(message, null);
    }

    public CryToStudentsDepartmentException(String message, Throwable cause) {
        super(message,  cause);
    }

}
