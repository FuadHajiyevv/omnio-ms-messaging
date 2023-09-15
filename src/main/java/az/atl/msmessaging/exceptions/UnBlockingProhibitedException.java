package az.atl.msmessaging.exceptions;

public class UnBlockingProhibitedException extends RuntimeException {
    public UnBlockingProhibitedException() {
    }

    public UnBlockingProhibitedException(String message) {
        super(message);
    }
}
