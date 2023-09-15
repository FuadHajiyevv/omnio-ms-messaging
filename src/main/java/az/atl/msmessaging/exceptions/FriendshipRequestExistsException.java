package az.atl.msmessaging.exceptions;

public class FriendshipRequestExistsException extends RuntimeException {
    public FriendshipRequestExistsException() {
    }

    public FriendshipRequestExistsException(String message) {
        super(message);
    }
}
