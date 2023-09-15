package az.atl.msmessaging.exceptions;

public class BlockingProhibitedException extends RuntimeException {

    public BlockingProhibitedException() {
    }

    public BlockingProhibitedException(String message) {
        super(message);
    }
}
