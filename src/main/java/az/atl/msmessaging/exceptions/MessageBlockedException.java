package az.atl.msmessaging.exceptions;

public class MessageBlockedException extends RuntimeException {
    public MessageBlockedException() {
    }

    public MessageBlockedException(String message) {
        super(message);
    }
}
