package az.atl.msmessaging.exceptions;

public class PendingStatusException extends RuntimeException {
    public PendingStatusException() {
    }

    public PendingStatusException(String message) {
        super(message);
    }
}
