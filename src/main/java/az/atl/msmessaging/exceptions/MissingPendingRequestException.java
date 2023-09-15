package az.atl.msmessaging.exceptions;

public class MissingPendingRequestException extends RuntimeException {
    public MissingPendingRequestException() {
    }

    public MissingPendingRequestException(String message) {
        super(message);
    }
}
