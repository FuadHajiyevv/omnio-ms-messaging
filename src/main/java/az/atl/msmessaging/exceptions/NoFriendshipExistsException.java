package az.atl.msmessaging.exceptions;

public class NoFriendshipExistsException extends RuntimeException {
    public NoFriendshipExistsException() {
    }

    public NoFriendshipExistsException(String message) {
        super(message);
    }
}
